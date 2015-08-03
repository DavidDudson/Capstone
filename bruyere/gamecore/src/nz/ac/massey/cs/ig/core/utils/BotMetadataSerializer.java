package nz.ac.massey.cs.ig.core.utils;

import java.util.Properties;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.BotData.BotMetaDataProperties;

public class BotMetadataSerializer {

	/**
	 * Small helper method to serialize bots into Properties
	 * @param botData
	 * @return
	 */
	public static Properties serialize(BotData botData) {
		Properties props = new Properties();
		
		for(BotMetaDataProperties property : BotMetaDataProperties.values()) {
			props.put(property.getName(), property.getValue(botData));
		}
		return props;
	}
}
