package me.angelfire.fandomconnect;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.fastily.jwiki.core.Wiki;
import me.angelfire.fandomconnect.commands.Fandom;
import me.angelfire.rpcard.utils.FileUtils;
import okhttp3.HttpUrl;



public final class FandomConnect extends JavaPlugin {

	public static FandomConnect INSTANCE;

    FileConfiguration languageConfig1;
    File languageFile1;
    File languageFile2;
    File languageFolder;
    File infoboxFolder;
    File infoboxPlayers;
    File infoboxTowns;
    File infoboxNations;
    Wiki wiki;


	@Override
    public void onEnable() {
		languageFolder = new File(this.getDataFolder(), "/languages");
        if (!languageFolder.isDirectory()){
            languageFolder.mkdir();
        }
		infoboxFolder = new File(this.getDataFolder(), "/infoboxes");
        if (!infoboxFolder.isDirectory()){
            infoboxFolder.mkdir();
        }
    	INSTANCE  = this;
    	createFiles();
        saveDefaultConfig();
        FandomConnect.createListingPages(INSTANCE.getWiki(), INSTANCE.getConfig());
        FandomConnect.createInfoboxes(INSTANCE.getWiki(), INSTANCE.getConfig());
        getCommand("fandom").setExecutor(new Fandom());
        if(this.getConfig().getString("data_to_send.rpcard.sync_at_restart").equals("true") && this.getConfig().getString("data_to_send.rpcard.integration_enabled").equals("true")) {
        	Fandom.syncPlayers();
        }
    }


	@Override
    public void onDisable() {
        // Plugin shutdown logic
    }

	public FileConfiguration getlanguageConfig() {
        languageConfig1 = new YamlConfiguration();
		if(INSTANCE.getConfig().getString("language").equals("fr_FR")) {
        return YamlConfiguration.loadConfiguration(languageFile2);
        }
		else {
			return YamlConfiguration.loadConfiguration(languageFile1);
		}
    }

	public File getInfobox(Integer infoboxType) {
		if (infoboxType == 1) {
			return new File(getDataFolder(), "infoboxes/" + FandomConnect.INSTANCE.getConfig().getString("infoboxes.players"));
		}
		else if (infoboxType == 2) {
			return new File(getDataFolder(), "infoboxes/" + FandomConnect.INSTANCE.getConfig().getString("infoboxes.towns"));
		}
		else if (infoboxType == 3) {
			return new File(getDataFolder(), "infoboxes/" + FandomConnect.INSTANCE.getConfig().getString("infoboxes.nations"));
		}
		else {
			return null;
		}
	}

    private void createFiles() {
        languageFile1 = new File(getDataFolder(), "languages/en_US.yml");
        if (!languageFile1.exists()) {
            languageFile1.getParentFile().mkdirs();
            saveResource("languages/en_US.yml", false);
         }

        languageFile2 = new File(getDataFolder(), "languages/fr_FR.yml");
        if (!languageFile2.exists()) {
            languageFile2.getParentFile().mkdirs();
            saveResource("languages/fr_FR.yml", false);
         }

        infoboxPlayers = new File(getDataFolder(), "infoboxes/" +INSTANCE.getConfig().getString("infoboxes.players"));
        if (!infoboxPlayers.exists()) {
            infoboxPlayers.getParentFile().mkdirs();
            saveResource("infoboxes/" + INSTANCE.getConfig().getString("infoboxes.players"), false);
         }

        infoboxTowns = new File(getDataFolder(), "infoboxes/" +INSTANCE.getConfig().getString("infoboxes.towns"));
        if (!infoboxTowns.exists()) {
            infoboxTowns.getParentFile().mkdirs();
            saveResource("infoboxes/" +INSTANCE.getConfig().getString("infoboxes.towns"), false);
         }

        infoboxNations = new File(getDataFolder(), "infoboxes/" +INSTANCE.getConfig().getString("infoboxes.nations"));
        if (!infoboxNations.exists()) {
            infoboxNations.getParentFile().mkdirs();
            saveResource("infoboxes/" +INSTANCE.getConfig().getString("infoboxes.nations"), false);
         }
    }

    public Wiki getWiki() {
        FileConfiguration config = INSTANCE.getConfig();
		if (config.getString("language").equals("fr_FR")) {
			wiki = new Wiki.Builder().withApiEndpoint(HttpUrl.parse(config.getString("links.fandom") + "/fr/api.php")).build();
			return wiki;
		}
		else {
			wiki = new Wiki.Builder().withApiEndpoint(HttpUrl.parse(config.getString("links.fandom") + "/api.php")).build();
			return wiki;
		}
    }

    public static void createListingPages(Wiki wiki, FileConfiguration config) {
		Wiki wiki2 = INSTANCE.getWiki();
		wiki2.login(config.getString("login.username"), config.getString("login.password"));
		FileConfiguration languageFile = INSTANCE.getlanguageConfig();
    	if(config.getString("data_to_send.rpcard.create_page_to_list_players").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.players"))) {
        		wiki2.addText(languageFile.getString("list_management.title.players"), "==" + languageFile.getString("list_management.title.players") + "==", languageFile.getString("list_management.reason.creation"), false);
        		Bukkit.getLogger().info("[FandomConnect] Listing page for players has been created !");
    		}
    	}
    	if(config.getString("data_to_send.towny.create_page_to_list_towns").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.towns"))) {
        		wiki2.addText(languageFile.getString("list_management.title.towns"), "=="+ languageFile.getString("list_management.title.towns") + "==", languageFile.getString("list_management.reason.creation"), false);
        		Bukkit.getLogger().info("[FandomConnect] Listing page for towns has been created !");
    		}
    	}
    	if(config.getString("data_to_send.towny.create_page_to_list_nations").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.nations"))) {
        		wiki2.addText(languageFile.getString("list_management.title.nations"), "==" + languageFile.getString("list_management.title.nations") + "==", languageFile.getString("list_management.reason.creation"), false);
        		Bukkit.getLogger().info("[FandomConnect] Listing page for nations has been created !");
    		}
    	}
    }

    public static void createInfoboxes(Wiki wiki, FileConfiguration config) {
		Wiki wiki2 = INSTANCE.getWiki();
		wiki2.login(config.getString("login.username"), config.getString("login.password"));
		FileConfiguration languageFile = INSTANCE.getlanguageConfig();
		String modele_base_name;
		if(config.getString("language").equals("fr_FR")) {
			modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Modèle:", "");
		}
		else {
			modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Template:", "");
		}
    	if(config.getString("data_to_send.rpcard.create_infobox_for_players").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.infobox_players"))) {
    				wiki2.addText(languageFile.getString("list_management.title.infobox_players"), FileUtils.loadContent(FandomConnect.INSTANCE.getInfobox(1)), languageFile.getString("list_management.reason.creation"), false);
    				Bukkit.getLogger().info("[FandomConnect] Infobox for players has been created !");
    		}
    	}
    	if(config.getString("data_to_send.towny.create_infobox_for_towns").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.infobox_towns"))) {
    				wiki2.addText(languageFile.getString("list_management.title.infobox_towns"), "{{" + modele_base_name + languageFile.getString("infobox.players"), languageFile.getString("list_management.reason.creation"), false);
    				Bukkit.getLogger().info("[FandomConnect] Infobox for towns has been created !");
				}
    	}
    	if(config.getString("data_to_send.towny.create_infobox_for_nations").equals("true")) {
    		if (!wiki2.exists(languageFile.getString("list_management.title.infobox_nations"))) {
    				wiki2.addText(languageFile.getString("list_management.title.infobox_nations"), "{{" + modele_base_name + languageFile.getString("nations.nations.custom"), languageFile.getString("list_management.reason.creation"), false);
    				Bukkit.getLogger().info("[FandomConnect] Infobox for nations has been created !");
    		}
    	}
    }
}
