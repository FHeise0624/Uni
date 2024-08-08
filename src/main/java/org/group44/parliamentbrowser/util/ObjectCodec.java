package org.group44.parliamentbrowser.util;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Wird für den MondoDbConnectionHandler benötigt um Daten korrekt zu speichern
 * 
 * @author Cora
 */
public class ObjectCodec implements Codec<Object> {

	@Override
	public void encode(BsonWriter writer, Object value, EncoderContext encoderContext) {
		if (value != null) {
			writer.writeString(value.toString());
		}
	}

	@Override
	public Class<Object> getEncoderClass() {
		return Object.class;
	}

	@Override
	public Object decode(BsonReader reader, DecoderContext decoderContext) {
		return reader.readString();
	}

}
