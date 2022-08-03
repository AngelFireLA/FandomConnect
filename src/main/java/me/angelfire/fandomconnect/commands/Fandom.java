package me.angelfire.fandomconnect.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import com.palmergames.bukkit.towny.TownyUniverse;

import io.github.fastily.jwiki.core.Wiki;
import me.angelfire.fandomconnect.FandomConnect;
import me.angelfire.rpcard.Profile;
import me.angelfire.rpcard.RpCard;
import me.angelfire.rpcard.json.ProfileSerializationManager;
import me.angelfire.rpcard.utils.FileUtils;

public class Fandom implements CommandExecutor, TabCompleter {

    FileConfiguration config = FandomConnect.INSTANCE.getConfig();
    FileConfiguration languageConfig = FandomConnect.INSTANCE.getlanguageConfig();

    @Override
    public List < String > onTabComplete(final CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Arrays.asList("toggle", "sync");
        if (args[0].equalsIgnoreCase("toggle") && args.length == 2) return Arrays.asList("rpcard", "towny");
        if (args[0].equalsIgnoreCase("sync") && args.length == 2) return Arrays.asList("all", "towns", "nations", "players");
        if (args[1].equalsIgnoreCase("rpcard") && args.length == 3) return Arrays.asList("page_creation");
        if (args[1].equalsIgnoreCase("towny") && args.length == 3) return Arrays.asList("towns", "nations");
        if ((args[2].equalsIgnoreCase("towns") && args.length == 4) || (args[2].equalsIgnoreCase("nations") && args.length == 4)) return Arrays.asList("page_creation");
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("toggle")) {
            if (args[1].equals("rpcard")) {
                if (args.length == 2) {
                    if (config.getString("data_to_send.rpcard.integration_enabled").equals("true")) {
                        config.set("data_to_send.rpcard.integration_enabled", "false");
                        sender.sendMessage(languageConfig.getString("rpcard.integration.disabled"));
                        FandomConnect.INSTANCE.saveConfig();
                    } else if (config.getString("data_to_send.rpcard.integration_enabled").equals("false")) {
                        config.set("data_to_send.rpcard.integration_enabled", "true");
                        FandomConnect.INSTANCE.saveConfig();
                        sender.sendMessage(languageConfig.getString("rpcard.integration.enabled"));
                    } else {
                        sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                    }
                } else if (args.length == 3) {
                    if (args[2].equals("page_creation")) {
                        if (config.getString("data_to_send.rpcard.create_page_to_list_players").equals("true")) {
                            config.set("data_to_send.rpcard.create_page_to_list_players", "false");
                            sender.sendMessage(languageConfig.getString("rpcard.create_page_to_list_players.disabled"));
                            FandomConnect.INSTANCE.saveConfig();
                        } else if (config.getString("data_to_send.rpcard.create_page_to_list_players").equals("false")) {
                            config.set("data_to_send.rpcard.create_page_to_list_players", "true");
                            FandomConnect.INSTANCE.saveConfig();
                            sender.sendMessage(languageConfig.getString("rpcard.create_page_to_list_players.enabled"));
                        } else {
                            sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                        }
                    }
                }
            } else if (args[1].equals("towny")) {
                if (args.length == 2) {
                    if (config.getString("data_to_send.towny.integration_enabled").equals("true")) {
                        config.set("data_to_send.towny.integration_enabled", "false");
                        sender.sendMessage(languageConfig.getString("towny.integration.disabled"));
                        FandomConnect.INSTANCE.saveConfig();
                    } else if (config.getString("data_to_send.rpcard.integration_enabled").equals("false")) {
                        config.set("data_to_send.towny.integration_enabled", "true");
                        FandomConnect.INSTANCE.saveConfig();
                        sender.sendMessage(languageConfig.getString("towny.integration.enabled"));
                    } else {
                        sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                    }
                } else if (args.length == 3) {
                    if (args[2].equals("town")) {
                        if (config.getString("data_to_send.town").equals("true")) {
                            config.set("data_to_send.towny.town", "false");
                            sender.sendMessage(languageConfig.getString("towny.town_data_sending.disabled"));
                            FandomConnect.INSTANCE.saveConfig();
                        } else if (config.getString("data_to_send.rpcard.town").equals("false")) {
                            config.set("data_to_send.towny.town", "true");
                            FandomConnect.INSTANCE.saveConfig();
                            sender.sendMessage(languageConfig.getString("towny.town_data_sending.enabled"));
                        } else {
                            sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                        }
                    } else if (args[2].equals("nation")) {
                        if (config.getString("data_to_send.towny.nation").equals("true")) {
                            config.set("data_to_send.towny.nation", "false");
                            sender.sendMessage(languageConfig.getString("towny.nation_data_sending.disabled"));
                            FandomConnect.INSTANCE.saveConfig();
                        } else if (config.getString("data_to_send.rpcard.nation").equals("false")) {
                            config.set("data_to_send.towny.nation", "true");
                            FandomConnect.INSTANCE.saveConfig();
                            sender.sendMessage(languageConfig.getString("towny.nation_data_sending.enabled"));
                        } else {
                            sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                        }
                    }
                }
                if (args[3].equals("page_creation") && args.length == 4) {

                    if (args[2].equals("town")) {
                        if (config.getString("data_to_send.towny.create_page_to_list_towns").equals("true")) {
                            config.set("data_to_send.towny.create_page_to_list_towns", "false");
                            sender.sendMessage(languageConfig.getString("towny.create_page_to_list_towns.disabled"));
                            FandomConnect.INSTANCE.saveConfig();
                        } else if (config.getString("data_to_send.towny.create_page_to_list_towns").equals("false")) {
                            config.set("data_to_send.towny.create_page_to_list_towns", "true");
                            FandomConnect.INSTANCE.saveConfig();
                            sender.sendMessage(languageConfig.getString("towny.create_page_to_list_towns.enabled"));
                        } else {
                            sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                        }
                    } else if (args[2].equals("nation")) {
                        if (config.getString("data_to_send.towny.create_page_to_list_nations").equals("true")) {
                            config.set("data_to_send.towny.create_page_to_list_nations", "false");
                            sender.sendMessage(languageConfig.getString("towny.create_page_to_list_nations.disabled"));
                            FandomConnect.INSTANCE.saveConfig();
                        } else if (config.getString("data_to_send.towny.create_page_to_list_nations").equals("false")) {
                            config.set("data_to_send.towny.create_page_to_list_nations", "true");
                            FandomConnect.INSTANCE.saveConfig();
                            sender.sendMessage(languageConfig.getString("towny.create_page_to_list_nations.enabled"));
                        } else {
                            sender.sendMessage(languageConfig.getString("error.neither_true_or_false"));
                        }
                    }
                }
            }
        } else if (args[0].equals("sync")) {

            if (args[1].equals("all")) {

            } else if (args[1].equals("towns")) {

            } else if (args[1].equals("nations")) {

            } else if (args[1].equals("players")) {
                if (config.getString("data_to_send.rpcard.integration_enabled").equals("true")) {
                    syncPlayers();
                }
            }
        }
        return false;
    }

    public static void syncPlayers() {
        Wiki wiki = FandomConnect.INSTANCE.getWiki();
        wiki.login(FandomConnect.INSTANCE.getConfig().getString("login.username"), FandomConnect.INSTANCE.getConfig().getString("login.password"));
        File folder = new File(RpCard.INSTANCE.getDataFolder(), "/profiles");
        final ProfileSerializationManager profileSerializationManager = RpCard.INSTANCE.getProfileSerializationManager();
        FileConfiguration languageFile = FandomConnect.INSTANCE.getlanguageConfig();

        for (File file: folder.listFiles()) {
            final String json = FileUtils.loadContent(file);
            final Profile profile = profileSerializationManager.deserialize(json);
            String modele_base_name;
            if (FandomConnect.INSTANCE.getConfig().getString("language").equals("fr_FR")) {
                modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Modèle:", "");
            } else {
                modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Template:", "");
            }
            String infobox = "{{" + modele_base_name + languageFile.getString("infobox.players");
            infobox = infobox.replaceAll("Exemple2", profile.getNomRp());
            infobox = infobox.replaceAll("Exemple1", profile.getPlayername());
            infobox = infobox.replaceAll("Exemple3", String.valueOf(profile.getAge()));
            if (FandomConnect.INSTANCE.getConfig().getString("language").equals("fr_FR")) {
                infobox = infobox.replaceAll("Exemple4", profile.getDeadoralive());
            } else {
                infobox = infobox.replaceAll("Exemple4", translator(profile.getDeadoralive()));
            }
            infobox = infobox.replaceAll("Exemple5", String.valueOf(profile.getTitre()));
            infobox = infobox.replaceAll("Exemple6", profile.getReligion());
            infobox = infobox.replaceAll("Exemple7", profile.getProfession());
            infobox = infobox.replaceAll("Exemple8", profile.getRace());

            if (TownyUniverse.getInstance().getResident(profile.getUuid()) != null) {
                if (TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() != null) {
                    infobox = infobox.replaceAll("Exemple01", TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName());
                    infobox = infobox.replaceAll("Exemple02", TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull().getName());
                } else if (TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull() != null && TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() == null) {
                    infobox = infobox.replaceAll("Exemple01", TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName());
                    infobox = infobox.replaceAll("Exemple02", "None");
                }
                else {
                    infobox = infobox.replaceAll("Exemple01", "None");
                    infobox = infobox.replaceAll("Exemple02", "None");
                }
                if (TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull() != null) {
                    infobox = infobox.replaceAll("Exemple9", String.valueOf(TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull().getHoldingBalance()));
                } else {
                    infobox = infobox.replaceAll("Exemple9", "???");
                }
            }
            if (!wiki.exists(profile.getPlayername())) {
                wiki.addText(profile.getPlayername(), infobox, languageFile.getString("list_management.reason.added_something.players"), false);
            }
            else {
                if (wiki.getTemplatesOnPage(profile.getPlayername()).contains(languageFile.getString("list_management.title.infobox_players"))) {
                	int start;
                	int end;

                    String pageText = wiki.getPageText(profile.getPlayername());

                    start = pageText.indexOf("|rp_name=");
                    end = pageText.indexOf("|age=");
                    pageText = new StringBuffer(pageText).replace(start, end, "|rp_name=" + profile.getNomRp()).toString();

                    start = pageText.indexOf("|age=");
                    end = pageText.indexOf("|dead_or_alive=");
                    pageText =new StringBuffer(pageText).replace(start, end, "|age= " + String.valueOf(profile.getAge())).toString();

                    if (FandomConnect.INSTANCE.getConfig().getString("language").equals("fr_FR")) {
                        pageText = pageText.replaceAll("dead_or_alive=Inconnu", "dead_or_alive=" + profile.getDeadoralive());
                        pageText = pageText.replaceAll("dead_or_alive=Vivant", "dead_or_alive=" + profile.getDeadoralive());
                        pageText = pageText.replaceAll("dead_or_alive=Mort", "dead_or_alive=" + profile.getDeadoralive());
                    } else {
                        pageText = pageText.replaceAll("dead_or_alive=Unkown", "dead_or_alive=" + translator(profile.getDeadoralive()));
                        pageText = pageText.replaceAll("dead_or_alive=Alive", "dead_or_alive=" + translator(profile.getDeadoralive()));
                        pageText = pageText.replaceAll("dead_or_alive=Dead", "dead_or_alive=" + translator(profile.getDeadoralive()));
                    }

                    start = pageText.indexOf("|titles=");
                    end = pageText.indexOf("|religion=");
                    pageText = new StringBuffer(pageText).replace(start, end, "|titles=" + profile.getTitre()).toString();

                    start = pageText.indexOf("|religion=");
                    end = pageText.indexOf("|jobs=");
                    pageText =new StringBuffer(pageText).replace(start, end, "|religion=" + profile.getReligion()).toString();

                    start = pageText.indexOf("|jobs=");
                    end = pageText.indexOf("|race=");
                    pageText =new StringBuffer(pageText).replace(start, end, "|jobs=" + profile.getProfession()).toString();

                    start = pageText.indexOf("|race=");
                    end = pageText.indexOf("|town=");
                    pageText =new StringBuffer(pageText).replace(start, end, "|race=" + profile.getRace()).toString();

                    if (TownyUniverse.getInstance().getResident(profile.getUuid()) != null) {
                        if (TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() != null) {
                            start = pageText.indexOf("|town=");
                            end = pageText.indexOf("|nation=");
                            pageText =new StringBuffer(pageText).replace(start, end, "|town=" + TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName()).toString();

                            start = pageText.indexOf("|nation=");
                            end = pageText.indexOf("}}");
                            pageText = new StringBuffer(pageText).replace(start, end, "|nation=" + TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull().getName()).toString();

                        } else if (TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull() != null && TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() == null) {
                            start = pageText.indexOf("|town=");
                            end = pageText.indexOf("|nation=");
                            pageText = new StringBuffer(pageText).replace(start, end, "|town=" + TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName()).toString();
                        }
                        if (TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull() != null) {
                        	start = pageText.indexOf("|money=");
                            end = pageText.indexOf("|town");
                            pageText = new StringBuffer(pageText).replace(start, end, "|money=" + String.valueOf(TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull().getHoldingBalance())).toString();
                        }
                    }
                    wiki.edit(profile.getPlayername(), pageText, "");
                }
                else {
                    wiki.addText(profile.getPlayername(), infobox, languageFile.getString("list_management.reason.added_something.players"), false);
                }
            }
            if (!(wiki.getPageText(languageFile.getString("list_management.title.players")).contains(profile.getPlayername()))) {
                wiki.addText(languageFile.getString("list_management.title.players"), "\r\n" +
                    "\r\n" +
                    "[[" + profile.getPlayername() + "]]", languageFile.getString("list_management.reason.added_something.players"), false);
            }
        }
    }

    public static String translator(String toTranslate) {
    	if(toTranslate.equals("Inconnu")) {
    		return "Unkown";
    	}
    	else if(toTranslate.equals("Vivant")) {
    		return "Alive";
    	}
    	else if(toTranslate.equals("Mort")) {
    		return "Dead";
    	}
    	else {
			return null;
		}
    }

}