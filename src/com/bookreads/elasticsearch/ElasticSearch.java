package com.bookreads.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.sun.xml.internal.ws.Closeable;

public enum ElasticSearch implements Closeable {
	INSTANCE;
	private static final int PORT = 9300;
	private static final String IPADDRESS = "127.0.0.1";
	private static final String BOOK_ES_CLUSTER = "book_es_cluster";
	private static final String CLUSTER_NAME = "cluster.name";
	public Client client;
	public TransportClient transportClient;

	public Client getClient() {
		if (client == null) {
			synchronized (this) {
				if (client == null) {
					Settings settings = ImmutableSettings.settingsBuilder().put(CLUSTER_NAME, BOOK_ES_CLUSTER).build();
					transportClient = new TransportClient(settings);
					client = transportClient.addTransportAddress(new InetSocketTransportAddress(IPADDRESS, PORT));
				}
			}
		}
		return client;
	}

	public void close() {
		if (client != null) {
			client.close();
		}
		if (transportClient != null) {
			transportClient.close();
		}
		client = null;
	}

}