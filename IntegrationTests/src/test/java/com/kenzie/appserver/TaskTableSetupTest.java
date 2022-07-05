package com.kenzie.appserver;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class TaskTableSetupTest {
    private static final String TASKS_TABLE_NAME = "Tasks";

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1).build());

    @Test
    public void taskTable_exists() {
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(TASKS_TABLE_NAME)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", TASKS_TABLE_NAME));
    }

}
