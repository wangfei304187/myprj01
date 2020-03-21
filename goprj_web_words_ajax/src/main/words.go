package main

import (
	"bufio"
	"fmt"
	"html/template"
	"io"
	"math/rand"
	"net/http"
	"os"
	"strings"
	"time"
)

var slice []string
var slice_en []string
var slice_zh []string

//func headers(w http.ResponseWriter, r *http.Request) {
//	w.Write([]byte("hi, " + time.Now().Format("2006/01/02 15:04:05") + "; Accept-Encoding: " + r.Header.Get("Accept-Encoding")))
//}

func copyright(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("Copyright ©2020 Words Corporation, All Rights Reserved"))
}

func words_en(w http.ResponseWriter, r *http.Request) {
	htmlStr := doRandHtml("en")
	w.Write([]byte(htmlStr))
}

func words_zh(w http.ResponseWriter, r *http.Request) {
	htmlStr := doRandHtml("zh")
	w.Write([]byte(htmlStr))
}

//rows := 8
//cols := 4
//wordsLang := "en"
//destination := wordsLang + "_words" + "_" + time.Now().Format("20060102_150405") + ".html"
func words(w http.ResponseWriter, r *http.Request) {
	fmt.Println("/words")
	fmt.Println(r.Method)
	fmt.Println(r.Host)
	size := r.ContentLength
	body := make([]byte, size)
	r.Body.Read(body)
	bodyStr := string(body)
	fmt.Println(bodyStr) // wordsLang=en

	//fmt.Println(typeof(bodyStr))
	//fmt.Println(len(bodyStr))

	var wordsLang string

	if len(bodyStr) == 0 {
		wordsLang = "en"

		htmlStr := doRandHtml(wordsLang)
		w.Write([]byte(htmlStr))

	} else {
		strSlice := strings.Split(bodyStr, "=")
		//		for i := 0; i < len(strSlice); i++ {
		//			fmt.Println(strSlice[i])
		//		}

		wordsLang = strSlice[1]
	}

}

func main() {
	server := http.Server{
		Addr: "127.0.0.1:8000",
	}

	// http.HandleFunc("/headers", headers)
	http.HandleFunc("/copyright", copyright)
	http.HandleFunc("/words", words)
	http.HandleFunc("/words/en", words_en)
	http.HandleFunc("/words/zh", words_zh)

	fmt.Println("start server...")
	server.ListenAndServe()
}

func doRandHtml(wordsLang string) string {

	if wordsLang == "en" {
		if slice_en == nil {
			fmt.Println("read words en")
			slice_en = readWords(wordsLang)
		}

		slice = slice_en
	} else if wordsLang == "zh" {
		if slice_zh == nil {
			fmt.Println("read words zh")
			slice_zh = readWords(wordsLang)
		}

		slice = slice_zh
	}

	rows := 8
	cols := 4
	batchSlice := extractBatchWords(slice, rows, cols)

	htmlStr := toHtml(batchSlice, rows, cols)

	return htmlStr
}

func doRandAjax(wordsLang string) string {

	if wordsLang == "en" {
		if slice_en == nil {
			fmt.Println("read words en")
			slice_en = readWords(wordsLang)
		}

		slice = slice_en
	} else if wordsLang == "zh" {
		if slice_zh == nil {
			fmt.Println("read words zh")
			slice_zh = readWords(wordsLang)
		}

		slice = slice_zh
	}

	rows := 8
	cols := 4
	batchSlice := extractBatchWords(slice, rows, cols)

	// htmlStr := toHtml(batchSlice, rows, cols)

	return batchSlice
}

func readWords(wordsLang string) []string {

	dictionaryFileName := wordsLang + "_words.txt" // zh_words.txt, en_words.txt
	fmt.Println(dictionaryFileName)

	fi, err := os.Open(dictionaryFileName)
	if err != nil {
		fmt.Printf("Error: %s\n", err)
		panic(err)
	}
	defer fi.Close()

	slice := make([]string, 0, 8000)
	dict := make(map[string]bool)
	var str string

	br := bufio.NewReader(fi)
	for {
		line, _, c := br.ReadLine()
		if c == io.EOF {
			break
		}

		str = strings.TrimSpace(string(line))
		if len(str) == 0 {
			continue
		}

		items := strings.Split(str, "|")

		for i := 0; i < len(items); i++ {
			//fmt.Println(strconv.Itoa(i) + "=>" + strings.TrimSpace(items[i]))

			wordStr := strings.TrimSpace(items[i])
			_, exist := dict[wordStr]
			if !exist {
				dict[wordStr] = true
				slice = append(slice, wordStr)
			} else {
				// do nothing
			}
		}
	}

	size := len(slice)
	fmt.Println("slice len:", size)
	fmt.Println()

	//	for k, v := range slice {
	//		fmt.Printf("slice[%d]=%s\n", k, v)
	//	}

	return slice
}

// slice --> batchSlice
func extractBatchWords(slice []string, rows int, cols int) []string {
	size := len(slice)
	batchSlice := make([]string, 0, rows*cols)
	rand.Seed(time.Now().Unix())
	total := 0
out:
	for i := 0; i < rows; i++ {
		for j := 0; j < cols; j++ {

			if total >= size {
				break out
			}

			index := rand.Intn(size)

			if exist(batchSlice, slice[index]) {
				j--
				continue
			} else {
				batchSlice = append(batchSlice, slice[index])
				total++
			}
			//fmt.Println(index, "=>", slice[index])
		}
		//fmt.Println()
	}

	//	for k, v := range batchSlice {
	//		fmt.Printf("batchSlice[%d]=%s\n", k, v)
	//	}

	return batchSlice
}

// batchSlice --> html string
func toHtml(batchSlice []string, rows int, cols int) string {
	tableElemStr := outputHtml(batchSlice, rows, cols)
	// fmt.Println(tableElemStr)

	fmt.Println("----------template.html-------------\n")

	tmpl, err := template.ParseFiles("template.html")
	if err != nil {
		panic(err)
	}

	var b = &strings.Builder{}
	err = tmpl.Execute(b, template.HTML(tableElemStr))
	if err != nil {
		panic(err)
	}

	return b.String()
}

func outputHtml(batchSlice []string, rows int, cols int) string {
	idx := 0
	s := "<table>\n"
	for i := 0; i < rows; i++ {
		s += "<tr>\n"
		for j := 0; j < cols; j++ {
			s += "<td>"
			if idx < len(batchSlice) {
				s += batchSlice[idx]
			}
			s += "</td>\n"
			idx++
		}
		s += "</tr>\n"
	}
	s += "</table>"
	return s
}

func exist(slice []string, str string) bool {
	result := false
	for _, v := range slice {
		// fmt.Println(v + ", " + str)
		if v == str {
			result = true
			break
		}
	}

	return result
}

//destination := wordsLang + "_words" + "_" + time.Now().Format("20060102_150405") + ".html"
func writeToFile(filepath string, msg string) {
	f, err := os.OpenFile(filepath, os.O_WRONLY|os.O_CREATE, 0666)
	defer f.Close()

	if err != nil {
		panic(err)
	}

	_, err = f.Write([]byte(msg))
	if err != nil {
		panic(err)
	}
}

func typeof(v interface{}) string {
	return fmt.Sprintf("%T", v)
}
