package com.jlearn.es.jest;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.*;
import io.searchbox.indices.ClearCache;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.Optimize;
import io.searchbox.indices.mapping.GetMapping;
import org.apache.http.impl.client.CloseableHttpClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author dingjuru
 * @date 2021/12/23
 */

@SpringBootTest
public class JestTtests {

    private static String indexName = "app_jest_index";
    private static String typeName = "_doc";

    @Autowired
    private JestClient jestClient;

    @Test
    void createIndex() {
        try {
            JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
            System.out.println("ES索引创建：" + jr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteIndex() {
        try{
            JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
            System.out.println(jr.isSucceeded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addDoc() {
        try{
            AppJestIndex appJestIndex = new AppJestIndex();
            appJestIndex.setId(1);
            appJestIndex.setName("测试-1");
            appJestIndex.setSex("男");
            appJestIndex.setAge(30);
            appJestIndex.setBooks(Arrays.asList("《每天阅读1小时》", "《非暴力沟通》"));

            Index index =
                    new Index.Builder(appJestIndex).id(String.valueOf(UUID.randomUUID()))
                            .index(indexName).type(typeName).build();
            JestResult jr = jestClient.execute(index);
            System.out.println("插入数据：" + jr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIndexMapping() {
        try{
            GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).build();
            JestResult jr = jestClient.execute(getMapping);
            System.out.println(jr.getJsonString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void batchInsert() {
        try{
            Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
            AppJestIndex app1 = new AppJestIndex();
            app1.setId(1);
            bulk.addAction(new Index.Builder(app1).id(app1.getId().toString()).build());

            AppJestIndex app2 = new AppJestIndex();
            app2.setId(2);
            bulk.addAction(new Index.Builder(app2).id(app2.getId().toString()).build());

            AppJestIndex app3 = new AppJestIndex();
            app3.setId(3);
            bulk.addAction(new Index.Builder(app3).id(app3.getId().toString()).build());

            BulkResult br = jestClient.execute(bulk.build());
            System.out.println("批量插入操作:"+br);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void pageQuery() {
        try{
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(10);

            // 查询全部
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());

            // 多条件查询
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("id", 1));
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", "测试-1"));
            searchSourceBuilder.query(boolQueryBuilder);

            String query = searchSourceBuilder.toString();
            Search search = new Search.Builder(query).addIndex(indexName).addType(typeName).build();
            SearchResult result = jestClient.execute(search);
            System.out.println("查询结果:" + result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按索引删除数据
     * @throws Exception
     */
    @Test
    void deleteData() throws Exception {
       try{
           DocumentResult dr =
                   jestClient.execute(new Delete.Builder(String.valueOf(1)).index(indexName).type(typeName).build());
           System.out.println(dr);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    /**
     * 索引优化
     */
    @Test
    void optimizeIndex() {
        Optimize optimize = new Optimize.Builder().build();
        jestClient.executeAsync(optimize, new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult jestResult) {
                System.out.println("OptimizeIndex result:{}" + jestResult);
            }

            @Override
            public void failed(Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 清理缓存
    @Test
    void clearCache() {
        try{
            ClearCache clearCache = new ClearCache.Builder().build();
            jestClient.execute(clearCache);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test() {

    }
}
