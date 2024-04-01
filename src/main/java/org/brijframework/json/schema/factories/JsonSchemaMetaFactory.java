package org.brijframework.json.schema.factories;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSchemaMetaFactory {

	private static final String LK = "LK@";

	private static final String CLASSES = "classes";

	private static final String REPLACEMENT = "/";

	private static final String REGEX = "\\\\";

	final ConcurrentHashMap<String, JsonSchemaObject> cache = new ConcurrentHashMap<String, JsonSchemaObject>();

	final String beans = "/beans";

	// singleton pattern
	private static JsonSchemaMetaFactory instance = null;

	public ConcurrentHashMap<String, JsonSchemaObject> getCache() {
		return cache;
	}

	public static JsonSchemaMetaFactory getInstance() {
		synchronized (JsonSchemaMetaFactory.class) {
			if (instance == null) {
				instance = new JsonSchemaMetaFactory();
			}
			return instance;
		}
	}

	private JsonSchemaMetaFactory() {
		this.init();
	}

	private void init() {
		URL resource = JsonSchemaMetaFactory.class.getResource(beans);
		try {
			Files.list(Paths.get(resource.toURI())).forEach(file -> {
				String resourcepath = file.toAbsolutePath().toString().split(CLASSES)[1];
				load(resourcepath);
			});

			for (JsonSchemaObject segmentMetaData : this.getCache().values()) {
				buildRelationship(segmentMetaData);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void load(String resourcepath) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			resourcepath = resourcepath.replaceAll(REGEX, REPLACEMENT);
			InputStream inJson = JsonSchemaMetaFactory.class.getResourceAsStream(resourcepath);
			JsonSchemaFile jsonSchemaFile = objectMapper.readValue(inJson, JsonSchemaFile.class);
			List<JsonSchemaObject> schemaObjects = jsonSchemaFile.getObjects();
			for (JsonSchemaObject segmentMetaData : schemaObjects) {
				getCache().put(segmentMetaData.getId(), segmentMetaData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildRelationship(JsonSchemaObject segmentMetaData) {
		Map<String, Object> properties = segmentMetaData.getProperties();
		properties.entrySet().forEach(entry -> {
			Object value = entry.getValue();
			if (value instanceof String) {
				String ref = value.toString();
				if (ref.startsWith(LK)) {
					String[] refInfos = ref.split(LK);
					JsonSchemaObject refInfo = this.getCache().get(refInfos[1].trim());
					entry.setValue(refInfo);
				}
			}
		});
		Map<String, Object> relationship = segmentMetaData.getRelationship();
		relationship.entrySet().forEach(entry -> {
			Object value = entry.getValue();
			if (value instanceof String) {
				String ref = value.toString();
				if (ref.startsWith(LK)) {
					String[] refInfos = ref.split(LK);
					JsonSchemaObject refInfo = this.getCache().get(refInfos[1].trim());
					entry.setValue(refInfo);
				}
			}
		});
	}

	public List<JsonSchemaObject> getAll(Class<? extends Object> type) {
		List<JsonSchemaObject> typeObjectList = new ArrayList<>();
		for (JsonSchemaObject segmentMetaData : this.getCache().values()) {
			if (segmentMetaData.getType().equals(type.getName())) {
				typeObjectList.add(segmentMetaData);
			}
		}
		return typeObjectList;
	}

}
