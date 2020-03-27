package main

import (
	"bytes"
	"fmt"
	"golang.org/x/text/encoding/simplifiedchinese"
	"golang.org/x/text/transform"
	"io/ioutil"
	"net/http"
	"os"
	"strconv"
	"strings"
	"time"
)

func main() {
	//	fmt.Println("args length: ", len(os.Args))
	//	for i, v := range os.Args {
	//		fmt.Printf("args[%v]=%v\n", i, v)
	//	}

	stockA := os.Args[1]
	stockB := os.Args[2]
	countB, err := strconv.Atoi(os.Args[3])
	if err != nil {
		fmt.Println("parse error", err)
		return
	}
	countA, err := strconv.Atoi(os.Args[4])
	if err != nil {
		fmt.Println("parse error", err)
		return
	}
	coeff, err := strconv.ParseFloat(os.Args[5], 32)
	if err != nil {
		fmt.Println("parse error", err)
		return
	}
	threshold, err := strconv.ParseFloat(os.Args[6], 32)
	if err != nil {
		fmt.Println("parse error", err)
		return
	}

	fmt.Println("INPUT:", stockA, stockB, countB, countA, coeff, threshold)
	// fmt.Println("证券代码Ａ | 证券名称Ａ | 转债代码Ｂ | 证券代码Ｂ | 证券Ａ现价 | 证券Ｂ现价 | 证券Ａ价值 | 证券Ｂ价值 | 计算结果 | 证券Ｂ数量 | 对应证券A数量 | 证券A数量 | 对应证券Ｂ数量 | 系数 | 报警阀值")
	// fmt.Println("证券代码Ａ | 证券名称Ａ | 转债代码Ｂ | 证券代码Ｂ |   证券Ａ现价 |   证券Ｂ现价 |   证券Ａ价值 |   证券Ｂ价值 |        计算结果 |   证券Ｂ数量 | 对应券A数量 |    证券A数量 | 对应券Ｂ数量 |           系数 |     报警阀值")
	fmt.Printf("%-5s | %-5s | %-5s | %-5s | %-7s | %-7s | %-7s | %-7s | %-11s | %-5s | %-7s | %-5s | %-7s | %-9s | %-s\n", "证券代码Ａ", "证券名称Ａ", "转债代码Ｂ", "证券代码Ｂ", "证券Ａ现价", "证券Ｂ现价", "证券Ａ价值", "证券Ｂ价值", "计算结果", "证券Ｂ数量", "对应证券Ａ数量", "证券Ａ数量", "对应证券Ｂ数量", "系数", "报警阀值")

	for {
		doReq(stockA, stockB, countA, countB, coeff, threshold)
		time.Sleep(time.Duration(5) * time.Second)
	}
}

//SH: 60, 90, 688
//SZ: 00, 20, 300

func generateQueryStr(stockCode string) string {
	s := ""
	if strings.HasPrefix(stockCode, "60") || strings.HasPrefix(stockCode, "90") || strings.HasPrefix(stockCode, "688") {
		s += "s_sh" + stockCode
	}
	if strings.HasPrefix(stockCode, "00") || strings.HasPrefix(stockCode, "20") || strings.HasPrefix(stockCode, "300") {
		s += "s_sz" + stockCode
	}

	return s
}

func doReq(stockA string, stockB string, countA int, countB int, coeff float64, threshold float64) {
	s := generateQueryStr(stockA)
	s += ","
	s += generateQueryStr(stockB)

	// http://hq.sinajs.cn/list=s_sz002230,s_sh600000
	urlStr := "http://hq.sinajs.cn/list=" + s
	// fmt.Println(urlStr)

	resp, err := http.Get(urlStr)
	// resp, err := http.Get("http://hq.sinajs.cn/list=sz002230,sh600000")
	if err != nil {
		fmt.Println("http get error", err)
		return
	}
	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("read error", err)
		return
	}
	//fmt.Println(typeof(body))
	//fmt.Println(string(body))
	utf8Bytes, err := GbkToUtf8(body)
	if err != nil {
		fmt.Println("GbkToUtf8 error", err)
		return
	}
	// fmt.Println(string(utf8Bytes))
	str := string(utf8Bytes)
	items := strings.Split(str, "\n")
	// fmt.Println("items:", len(items))

	var nameA string
	var priceA float64
	var valueA float64
	var estimateCountA float64

	var nameB string
	var priceB float64
	var valueB float64
	var estimateCountB float64

	var result float64

	//	priceA = 9.63
	//	priceB = 156.66

	for i := 0; i < len(items); i++ {
		item := items[i]

		if len(strings.TrimSpace(item)) == 0 {
			continue
		}
		// fmt.Println(item)

		idxStart := strings.Index(item, "\"")
		idxEnd := strings.LastIndex(item, "\"")

		// fmt.Println(idxStart, idxEnd)
		// fmt.Println(item[idxStart+1 : idxEnd])

		s := item[idxStart+1 : idxEnd]
		simpleData := strings.Split(s, ",")
		name := simpleData[0]
		price, _ := strconv.ParseFloat(simpleData[1], 64)

		if i == 0 {
			nameA = name
			priceA = price
			valueA = priceA / coeff * 100
			estimateCountA = float64(countB) / coeff * 100
		} else if i == 1 {
			nameB = name
			priceB = price
			valueB = priceB / 100 * coeff
			estimateCountB = float64(countA) / 100 * coeff
		}
	}

	result = (valueA - priceB) / valueA * 100

	fmt.Printf("%-9s | %-s | %-8s | %-s | %9.4f | %9.4f | %9.4f | %9.4f | %9.4f%% | %8d | %12.4f | %8d | %12.4f | %6.2f | %9.4f\n", stockA, nameA, stockB, nameB, priceA, priceB, valueA, valueB, result, countB, estimateCountA, countA, estimateCountB, coeff, threshold)

	// fmt.Println(stockA + " | " + nameA + " | " + stockB + " | " + nameB + " | " + FloatToString(priceA) + " | " + FloatToString(priceB) + " | " + FloatToString(valueA) + " | " + FloatToString(valueB) + " | " + FloatToString(result) + "% | " + strconv.Itoa(countB) + " | " + FloatToString(estimateCountA) + " | " + strconv.Itoa(countA) + " | " + FloatToString(estimateCountB) + " | " + FloatToString(coeff) + " | " + FloatToString(threshold))
}

func FloatToString(f float64) string {
	return strconv.FormatFloat(float64(f), 'f', 6, 64)
}

// transform GBK bytes to UTF-8 bytes
func GbkToUtf8(str []byte) (b []byte, err error) {
	r := transform.NewReader(bytes.NewReader(str), simplifiedchinese.GBK.NewDecoder())
	b, err = ioutil.ReadAll(r)
	if err != nil {
		return
	}
	return
}

// transform UTF-8 bytes to GBK bytes
func Utf8ToGbk(str []byte) (b []byte, err error) {
	r := transform.NewReader(bytes.NewReader(str), simplifiedchinese.GBK.NewEncoder())
	b, err = ioutil.ReadAll(r)
	if err != nil {
		return
	}
	return
}

// transform GBK string to UTF-8 string and replace it, if transformed success, returned nil error, or died by error message
func StrToUtf8(str *string) error {
	b, err := GbkToUtf8([]byte(*str))
	if err != nil {
		return err
	}
	*str = string(b)
	return nil
}

// transform UTF-8 string to GBK string and replace it, if transformed success, returned nil error, or died by error message
func StrToGBK(str *string) error {
	b, err := Utf8ToGbk([]byte(*str))
	if err != nil {
		return err
	}
	*str = string(b)
	return nil
}

func typeof(v interface{}) string {
	return fmt.Sprintf("%T", v)
}

//import (
//	"fmt"
//	"github.com/gocolly/colly"
//)
//
//func main() {
//
//	// Instantiate default collector
//
//	c := colly.NewCollector()
//
//	// Visit only domains: hackerspaces.org, wiki.hackerspaces.org
//
//	c.AllowedDomains = []string{"hackerspaces.org", "wiki.hackerspaces.org"}
//
//	// On every a element which has href attribute call callback
//
//	c.OnHTML("a[href]", func(e *colly.HTMLElement) {
//
//		link := e.Attr("href")
//
//		// Print link
//
//		fmt.Printf("Link found: %q -> %s\n", e.Text, link)
//
//		// Visit link found on page
//
//		// Only those links are visited which are in AllowedDomains
//
//		c.Visit(e.Request.AbsoluteURL(link))
//
//	})
//
//	// Before making a request print "Visiting ..."
//
//	c.OnRequest(func(r *colly.Request) {
//
//		fmt.Println("Visiting", r.URL.String())
//
//	})
//
//	// Start scraping on https://hackerspaces.org
//
//	c.Visit("https://hackerspaces.org/")
//
//}
