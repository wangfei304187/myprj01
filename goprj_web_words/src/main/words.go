package main

import (
	"bufio"
	"flag"
	"fmt"
	"html/template"
	"io"
	"math/rand"
	"os"
	"strings"
	"time"
)

func main() {
	fmt.Println("args length: ", len(os.Args))

	for i, v := range os.Args {
		fmt.Printf("args[%v]=%v\n", i, v)
	}

	str, _ := os.Getwd()
	fmt.Println("work dir: " + str)

	////////////////////////////////////////

	rows := 8
	cols := 4
	wordsLang := "en"
	//destination := wordsLang + "_words" + "_" + time.Now().Format("20060102_150405") + ".html"

	flag.IntVar(&rows, "r", rows, "rows")
	flag.IntVar(&cols, "c", cols, "cols")
	flag.StringVar(&wordsLang, "w", wordsLang, "zh--Chinese, en--English")
	//flag.StringVar(&destination, "d", destination, "destination")

	flag.Parse()

	//	fmt.Printf("rows=%v, cols=%v, wordsLang=%v, destination=%v\n", rows, cols, wordsLang, destination)
	fmt.Printf("rows=%v, cols=%v, wordsLang=%v\n", rows, cols, wordsLang)

	// file --> slice

	dictionaryFileName := wordsLang + "_words.txt" // zh_words.txt, en_words.txt
	destination := wordsLang + "_words" + "_" + time.Now().Format("20060102_150405") + ".html"

	fi, err := os.Open(dictionaryFileName)
	if err != nil {
		fmt.Printf("Error: %s\n", err)
		return
	}
	defer fi.Close()

	slice := make([]string, 0, 8000)
	dict := make(map[string]bool)

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

	//////////////////////////////////////////////

	// slice --> batchSlice

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

	//////////////////////////////////////////////

	// batchSlice --> html string

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

	writeToFile(destination, b.String())

	fmt.Println("done. " + destination)
}

func outputHtml(slice []string, rows int, cols int) string {
	idx := 0
	s := "<table>\n"
	for i := 0; i < rows; i++ {
		s += "<tr>\n"
		for j := 0; j < cols; j++ {
			s += "<td>"
			if idx < len(slice) {
				s += slice[idx]
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
