package curl;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FilterDirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Title:TestQuery.java
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
public class TestQuery {
	public static void main(String[] args) throws IOException {
		ScoreDoc[] hits = null;
		TopDocs results = null;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("/home/gsj/doc/sf1/lucene/s")));
	    IndexSearcher searcher = new IndexSearcher(reader);
		String field = "contents";
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		String queryString = "中华";
		Query query = null;
		try {
			QueryParser parser = new QueryParser(Version.LUCENE_40, field, analyzer);
			query = parser.parse(queryString);
			results = searcher.search(query, 5 * 10);
			hits = results.scoreDocs;
		} catch (ParseException e) {
		}
		if (searcher != null) {
			if (hits.length > 0) {
				System.out.println(" 找到: " + hits.length + "  个结果! ");
			}
		}
	}
}
