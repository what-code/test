/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 *   wuhua <wq163@163.com> , boyan <killme2008@gmail.com>
 */
package example;

import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;


/**
 * 
 * @author
 * @since 2012-2-20 14:36:51
 */

public class Help {
    public static MetaClientConfig initMetaConfig() {
        final MetaClientConfig metaClientConfig = new MetaClientConfig();
        final ZKConfig zkConfig = new ZKConfig();
        //zkConfig.zkConnect = "172.16.7.83:2181";
        zkConfig.zkConnect = "10.10.99.167:2181";
        //zkConfig.zkRoot = "/meta";
        metaClientConfig.setZkConfig(zkConfig);
        return metaClientConfig;
    }
}