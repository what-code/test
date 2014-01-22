package elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * Title:TestClient.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-8-21
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestClient {
	public static void main(String[] args) {
		Node node = NodeBuilder.nodeBuilder().clusterName("elasticsearch_gsj").client(true).node(); 
		Client client = node.client(); 
		QueryBuilder qb1 = QueryBuilders.termQuery("", "");
		//qb1
		//关闭节点  
		node.close();
	}
}
