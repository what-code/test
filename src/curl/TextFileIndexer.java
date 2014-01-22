package curl;

import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;

/**
 * Title:TextFileIndexer.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-8-13
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TextFileIndexer {
	public static void main(String[] args) throws Exception {
		/* 指明要索引文件夹的位置,这里是C盘的S文件夹下 */
		File fileDir = new File("/home/gsj/doc/sf1/lucene/s");

		/* 这里放索引文件的位置 */
		File indexDir = new File("/home/gsj/doc/sf1/lucene/i");
		Analyzer luceneAnalyzer = new ChineseAnalyzer();
		Directory dic = new SimpleFSDirectory(fileDir);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				luceneAnalyzer);
		IndexWriter indexWriter = new IndexWriter(dic, iwc);
		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();

		// 增加document到索引去
		for (int i = 0; i < textFiles.length; i++) {
			if (textFiles[i].isFile()
					&& textFiles[i].getName().endsWith(".txt")) {
				System.out.println(" File  " + textFiles[i].getCanonicalPath()
						+ " 正在被索引. ");
				String temp = FileReaderAll(textFiles[i].getCanonicalPath(),
						"UTF-8");
				System.out.println(temp);
				Document document = new Document();
				Field FieldPath = new Field("path", textFiles[i].getPath(),
						Field.Store.YES, Field.Index.NO);
				Field FieldBody = new Field("body", temp, Field.Store.YES,
						Field.Index.ANALYZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS);
				document.add(FieldPath);
				document.add(FieldBody);
				indexWriter.addDocument(document);
			}
		}
		// optimize()方法是对索引进行优化
		// ((Object) indexWriter).optimize();
		indexWriter.close();

		// 测试一下索引的时间
		long endTime = new Date().getTime();
		System.out.println(" 这花费了 " + (endTime - startTime)
				+ "  毫秒来把文档增加到索引里面去! " + fileDir.getPath());
	}

	public static String FileReaderAll(String FileName, String charset)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(FileName), charset));
		String line = new String();
		String temp = new String();

		while ((line = reader.readLine()) != null) {
			temp += line;
		}
		reader.close();
		return temp;
	}
}
