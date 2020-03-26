package main

import (
	"bytes"
	"fmt"
	"golang.org/x/text/encoding/simplifiedchinese"
	"golang.org/x/text/transform"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

func main() {
	for {
		doReq()
		time.Sleep(time.Duration(5) * time.Second)
	}
}

//SH: 60, 90, 688
//SZ: 00, 20, 300

func doReq() {
	resp, err := http.Get("http://hq.sinajs.cn/list=s_sz002230,s_sh600000")
	// resp, err := http.Get("http://hq.sinajs.cn/list=sz002230,sh600000")
	if err != nil {
		fmt.Println("http get error", err)
		return
	}
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
<<<<<<< HEAD
		// name := simpleData[0]
		price := simpleData[1]

//		fmt.Println(name, price)
		fmt.Println(price)
=======
		name := simpleData[0]
		price := simpleData[1]

		fmt.Println(name, price)
>>>>>>> a6903bdfd0dc8c6deeb64c8a397876aaeb8e790d
	}
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
<<<<<<< HEAD
//}
=======
//}
>>>>>>> a6903bdfd0dc8c6deeb64c8a397876aaeb8e790d
