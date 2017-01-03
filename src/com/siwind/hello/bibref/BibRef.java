/**
 * 
 */
package com.siwind.hello.bibref;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jdk.internal.dynalink.beans.StaticClass;


/**
 * @author admin
 *
 */
public class BibRef {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		parseBibXml();
	}

	/**
	 * 
	 */
	public static void parseBibXml() {
		String strDir = "D:/Temp/rfc/";
		ArrayList<BibItem> bibs = parseXml(strDir + "rfc-index.xml");
		//ArrayList<BibItem> bibs = parseXml(strDir + "a.xml");
		
		writeBib2FileXml(strDir + "myrfc-index.xml", bibs);
		//writeBib2File("D:/myrfc.bib", bibs);
		writeBib2File(strDir + "rfc.bib", bibs);
		
		writeBib2Php(strDir + "rfc-inc.php",bibs);
		System.out.println("Done!");
	}
	
	/**
	 * 
	 * @param fileName
	 * @param bibs
	 */
	public static void writeBib2Php(String fileName, ArrayList<BibItem> bibs){
		BufferedWriter out = null;
		String crlf = System.getProperty("line.separator");
		String str = "";
		
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			
			str = 
"<?php " + crlf + 
"define('RFC_MAX'," + bibs.get(bibs.size()-1).id + ");" + crlf +
"// return array('BIB'=>...,  'URL'=>...) " + crlf +
"function getBibItem($rfcid){" + crlf +
"static $bInit = false;" + crlf + 
"static $arr= array();"  + crlf +  
"if( !$bInit ){" + crlf ;
			
			out.write(str);			
			for (int i = 0; i < bibs.size(); i++) {
				BibItem it = bibs.get(i);
				
				out.write("$arr[" + it.id + "] = <<<EOT"+ crlf);
				out.write(it.genBibItemStr());
				out.write("EOT;"+crlf);

				//out.newLine();
			}

			str = 
"$bInit = true;}" + crlf + 
"$rfc = intval($rfcid);" + crlf +
"$res = array('BIB'=>NULL, 'URL'=>NULL);" + crlf +
"if( ($rfc>=1) and ($rfc<=RFC_MAX) ){" + crlf +
"\t$res['BIB'] = htmlspecialchars($arr[$rfc]);" + crlf + 
"\tif( !is_null( $arr[$rfc]) ) {$res['URL']=\"http://tools.ietf.org/html/rfc$rfc\";}" + crlf +
"}" + crlf +
"return $res;" + crlf +
"} " + crlf +
"?>";

			out.write(str);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public static void writeBib2FileXml(String fileName, ArrayList<BibItem> bibs) {
		BufferedWriter out = null;
		String crlf = System.getProperty("line.separator");
		
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+crlf);
			out.write("<rfc-index>" + crlf);
			for (int i = 0; i < bibs.size(); i++) {
				out.write(bibs.get(i).genBibXml());
				//out.newLine();
			}
			out.write("</rfc-index>" + crlf);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	// 注意：上面的例子由于写入的文本很少，使用FileWrite类就可以了。但如果需要写入的
	// 内容很多，就应该使用更为高效的缓冲器流类BufferedWriter。
	/**
	 * 使用BufferedWriter类写文本文件
	 */
	public static void writeBib2File(String fileName, ArrayList<BibItem> bibs) {
		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < bibs.size(); i++) {
				out.write(bibs.get(i).genBibItemStr());
				out.newLine();
			}
			// out.write("Hello Kuka:");
			// out.newLine(); //注意\n不一定在各种计算机上都能产生换行的效果
			// out.write(" My name is coolszy!\n");
			// out.write(" I like you and miss you。");
			// out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public static ArrayList<BibItem> parseXml(String fileName) {
		ArrayList<BibItem> bibs = new ArrayList<BibItem>();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList nodes = document.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node entries = nodes.item(i);
				NodeList itemInfo = entries.getChildNodes();
				for (int j = 0; j < itemInfo.getLength(); j++) {
					Node node = itemInfo.item(j);
					if (!node.getNodeName().equals("rfc-entry"))
						continue;
					;

					bibs.add(BibItem.getItem(node.getChildNodes()));

					// System.out.println( node.getNodeName() );
					// NodeList bibInfos = node.getChildNodes();
					// node.getUserData("");
				}

				// write to file
				//writeBib2File(outName, bibs);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bibs;
	}

}


//============================
class BibItem {
	/**
	 * RFC NUMBER
	 */
	int id;
	String rfcid;
	String docid;
	String title;
	String author = "";
	String year;
	String month;
	String doi;
	String cur_status;
	String pub_status;
	String issn;
	String pages;
	

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
		this.rfcid = docid.substring(3);
		this.id = Integer.parseInt(this.rfcid); //id
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	/**
	 * 
	 * @return Bib Itm String
	 * @TechReport{rfc4271, author = {Y. Rekhter and T. Li and S. Hares}, title
	 *                      = {A Border Gateway Protocol 4 (BGP-4)},
	 *                      TYPE="{RFC}", NUMBER=4271, PUBLISHER = "{RFC
	 *                      Editor}", institution = {Internet Engineering Task
	 *                      Force}, year = {2006}, MONTH = {July}, ISSN =
	 *                      {2070-1721}, doi = {10.17487/RFC4271}, url =
	 *                      {http://www.ietf.org/rfc/rfc1654.txt}, }
	 */
	public String genBibItemStr() {
		String crlf = System.getProperty("line.separator");

		StringBuilder str = new StringBuilder("@TechReport{rfc" + rfcid + "," + crlf);
		// str.append(docid + ",\r\n").append(crlf);
		str.append("\tAUTHOR = \"" + author + "\",").append(crlf);
		str.append("\tTITLE = \"{" + title + "}\",").append(crlf);
		str.append("\tTYPE = \"{" + "RFC" + "}\",").append(crlf);
		str.append("\tNUMBER = \"" + rfcid + "\",").append(crlf);
		str.append("\tINSTITUTION = \"{" + "Internet Engineering Task Force" + "}\",").append(crlf);
		str.append("\tPUBLISHER = \"{" + "RFC Editor" + "}\",").append(crlf);
		str.append("\tYEAR = " + year + ",").append(crlf);
		str.append("\tMONTH = \"" + month + "\",").append(crlf);
		if( !issn.isEmpty() ) str.append("\tISSN = \"{" + issn + "}\",").append(crlf); 
		if( !pages.isEmpty() ) str.append("\tPAGES = \"1-" + pages + "\",").append(crlf);
		str.append("\tDOI = \"{" + doi + "}\",").append(crlf);
		str.append("\tHOWPUBLISHED = \"{" + "Internet Requests for Comments" + "}\",").append(crlf);
		str.append("\tURL = \"{" + "http://www.rfc-editor.org/rfc/rfc" + id + ".txt" + "}\"").append(crlf);
		str.append("}").append(crlf);

		return str.toString();
	}

	public String genBibXml() {
		String crlf = System.getProperty("line.separator");
		
		StringBuilder str = new StringBuilder("<rfc-entry>"+crlf);
		
		str.append("<rfc-id>" + rfcid + "</rfc-id>").append(crlf);
		str.append("<doc-id>" + docid + "</doc-id>").append(crlf);
		str.append("<author>" + author + "</author>").append(crlf);
		str.append("<title>" + title + "</title>").append(crlf);
		str.append("<year>" + year + "</year>").append(crlf);
		str.append("<month>" + month + "</month>").append(crlf);
		str.append("<doi>" + doi + "</doi>").append(crlf);
		str.append("<issn>" + issn + "</issn>").append(crlf);
		str.append("<pages>" + pages + "</pages>").append(crlf);
		str.append("<current-status>" + cur_status + "</current-status>").append(crlf);
		str.append("<publication-status>" + pub_status + "</publication-status>").append(crlf);
		
		str.append("</rfc-entry>").append(crlf);

		return str.toString();
	}

	/**
	 * http://www.ietf.org/rfc/rfc1654.txt
	 */
	public BibItem genISSN_Pages(){
		String strUrl = "http://www.ietf.org/rfc/rfc" + id + ".txt";
		String strContent = WebUtil.getHtmlContent(strUrl);
		this.issn = WebUtil.getISSN(strContent);
		this.pages = WebUtil.getPageNum(strContent);
		return this;
	}
	/**
	 * 
	 */
	public void print() {
		System.out.println(
				"RFCNum=" + rfcid + ", doc-id=" + docid + ", title=" + title + ", author=" + author + ",  year=" + year
						+ ", month= " + month + ", cur-status=" + cur_status + ", pub-status=" + pub_status);
	}

	/**
	 * 
	 * @param bibInfos
	 * @return
	 */
	public static BibItem getItem(NodeList bibInfos) {
		BibItem item = new BibItem();

		for (int i = 0; i < bibInfos.getLength(); i++) {
			Node node = bibInfos.item(i);

			// System.out.println(node.getNodeValue());

			if (node.getNodeName().equals("#text")) {
				continue;
			}
			if (node.getNodeName().equals("doc-id")) {

				// item.docid = node.getTextContent();
				item.setDocid(node.getTextContent());

			} else if (node.getNodeName().equals("title")) {
				item.title = node.getTextContent();
			} else if (node.getNodeName().equals("author")) {
				NodeList ls = node.getChildNodes();
				for (int j = 0; j < ls.getLength(); j++) {
					Node nd = ls.item(j);
					if (nd.getNodeName().equals("name")) {
						if (!item.author.isEmpty()) {
							item.author += " and ";
						}
						item.author += nd.getTextContent();
					}
				}
			} else if (node.getNodeName().equals("doi")) {
				item.doi = node.getTextContent();
			} else if (node.getNodeName().equals("date")) {
				NodeList ls = node.getChildNodes();
				for (int j = 0; j < ls.getLength(); j++) {
					Node node2 = ls.item(j);
					if (node2.getNodeName().equals("month")) {
						item.month = node2.getTextContent();
					} else if (node2.getNodeName().equals("year")) {
						item.year = node2.getTextContent();
					}
				}
			} else if (node.getNodeName().equals("current-status")) {
				item.cur_status = node.getTextContent();
			} else if (node.getNodeName().equals("publication-status")) {
				item.pub_status = node.getTextContent();
			} else {
				// System.out.println(node.getNodeName() + " <--> " +
				// node.getTextContent());
			}
		} // end of for

		// item.print();
		// System.out.println(item.genBibItemStr());
		return item.genISSN_Pages();
	}
}

class WebUtil{
	
	static StringBuilder stringBuilder = null;
	static{
		stringBuilder = new StringBuilder(1024*1024);   // pre allocate memory to speed up url accessing.
	}
	public static String getHtmlContent(String htmlurl) {  
		
		return getHtmlContent(htmlurl,stringBuilder);
	}
    /**  
     * 读取网页全部内容  
     */  
    public static String getHtmlContent(String htmlurl, StringBuilder sb) {  
        URL url;  
        String temp;  
        //StringBuilder sb = new StringBuilder("");  
        sb.setLength(0);
        System.out.println("访问URL : " + htmlurl);
        
        BufferedReader in = null;
        try {  
            url = new URL(htmlurl);  
            in = new BufferedReader(new InputStreamReader(url.openStream()));// 读取网页全部内容 , default char-set
            while ((temp = in.readLine()) != null) {  
                sb.append(temp);  
            }  
            in.close();  
        } catch ( MalformedURLException me) {  
            System.out.println("你输入的URL格式有问题!");  
            me.getMessage();  
        } catch ( FileNotFoundException e) {  
        	System.out.println("网页未找到! URL=" + htmlurl);    
        }  catch (Exception e) {
			// TODO: handle exception
		}finally {
			if( in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return sb.toString();  
    }  
    
    /**
     * 
     * @param strContent
     * @return
     */
    public static String getISSN(String strContent){
    	String str = "";
    	int index = strContent.indexOf("ISSN: ");
    	if( index>=0 ){
    		index += 6; //skip "ISSN: "
    		int lastindex = index;
    		while (lastindex<strContent.length() && !Character.isWhitespace(strContent.charAt(lastindex))) 	lastindex ++;

    		str = strContent.substring(index, lastindex).trim();
    	}
    	return str;
    }
    /**
     * 
     * @param strContent
     * @return
     */
    public static String getPageNum(String strContent){
    	String str = "";
    	int index = strContent.lastIndexOf("[Page ");  // [Page 16]
    	if( index>=0 ){
    		index +=6; //skip "[Page "
    		int lastindex = index;
    		
    		while(lastindex<strContent.length() && Character.isDigit(strContent.charAt(lastindex))) lastindex++;
    		while (lastindex<strContent.length() && Character.isWhitespace(strContent.charAt(lastindex))) 	lastindex ++;
    		
    		if( strContent.charAt(lastindex) == ']'){
    			str = strContent.substring(index, lastindex).trim();
    		}
    	}
    	return str;
    }
}
