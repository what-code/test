package lucene;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.apache.lucene.util._TestUtil;

public class TestDemo {

	private void testOneSearch(File indexPath, String query,
			int expectedHitCount) throws Exception {
		PrintStream outSave = System.out;
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			PrintStream fakeSystemOut = new PrintStream(bytes, false, Charset
					.defaultCharset().name());
			System.setOut(fakeSystemOut);
			SearchFiles.main(new String[]{"-query", query, "-index",
					indexPath.getPath() });
			fakeSystemOut.flush();
			String output = bytes.toString(Charset.defaultCharset().name());
		} finally {
			System.setOut(outSave);
		}
	}

	public void testIndexSearch() throws Exception {
		File dir = new File("/home/gsj/doc/sf1/lucene/s");
		File indexDir = new File("/home/gsj/doc/sf1/lucene/i");
		IndexFiles.main(new String[] { "-create", "-docs", dir.getPath(),
				"-index", indexDir.getPath() });
		//testOneSearch(indexDir, "中华", 0);
	}

	public static void main(String[] args) {
		/*try {
			new TestDemo().testIndexSearch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		new Thread(new TestRunnable()).start();
	}
}
