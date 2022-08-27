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

	@Override
	public List < String > onTabComplete(final CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
		if (args.length == 1) return Arrays.asList("toggle", "sync", "reload");
		if (args[0].equalsIgnoreCase("toggle") && args.length == 2) return Arrays.asList("rpcard", "towny");
		if (args[0].equalsIgnoreCase("sync") && args.length == 2) return Arrays.asList(/*"all", "towns", "nations", */"players");
		if (args[1].equalsIgnoreCase("rpcard") && args.length == 3) return Arrays.asList("page_creation", "infobox_creation");
		if (args[1].equalsIgnoreCase("towny") && args.length == 3) return Arrays.asList("town", "nation");
		if ((args[2].equalsIgnoreCase("town") && args.length == 4) || (args[2].equalsIgnoreCase("nation") && args.length == 4)) return Arrays.asList("page_creation", "infobox_creation");
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration languageFile = FandomConnect.INSTANCE.getlanguageConfig();
		if (args[0].equals("toggle")) {
			if (args[1].equals("rpcard")) {
				if (args.length == 2) {
					if (config.getString("rpcard.integration_enabled").equals("true")) {
						config.set("rpcard.integration_enabled", "false");
						sender.sendMessage(languageFile.getString("rpcard.integration.disabled"));
						FandomConnect.INSTANCE.saveConfig();
					} else if (config.getString("rpcard.integration_enabled").equals("false")) {
						config.set("rpcard.integration_enabled", "true");
						FandomConnect.INSTANCE.saveConfig();
						sender.sendMessage(languageFile.getString("rpcard.integration.enabled"));
					} else {
						sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
					}
				} else if (args.length == 3) {
					if (args[2].equals("page_creation")) {
						if (config.getString("rpcard.create_page_to_list_players").equals("true")) {
							config.set("rpcard.create_page_to_list_players", "false");
							sender.sendMessage(languageFile.getString("rpcard.create_page_to_list_players.disabled"));
							FandomConnect.INSTANCE.saveConfig();
						} else if (config.getString("rpcard.create_page_to_list_players").equals("false")) {
							config.set("rpcard.create_page_to_list_players", "true");
							FandomConnect.INSTANCE.saveConfig();
							sender.sendMessage(languageFile.getString("rpcard.create_page_to_list_players.enabled"));
						} else {
							sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
						}
					}
					if (args[2].equals("infobox_creation")) {
						if (config.getString("rpcard.create_infobox_for_players").equals("true")) {
							config.set("rpcard.create_infobox_for_players", "false");
							sender.sendMessage(languageFile.getString("rpcard.create_an_infobox_for_players.disabled"));
							FandomConnect.INSTANCE.saveConfig();
						} else if (config.getString("rpcard.create_infobox_for_players").equals("false")) {
							config.set("rpcard.create_infobox_for_players", "true");
							FandomConnect.INSTANCE.saveConfig();
							sender.sendMessage(languageFile.getString("rpcard.create_infobox_for_players.enabled"));
						} else {
							sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
						}
					}
				}
			} else if (args[1].equals("towny")) {
				if (args.length == 2) {
					if (config.getString("towny.integration_enabled").equals("true")) {
						config.set("towny.integration_enabled", "false");
						sender.sendMessage(languageFile.getString("towny.integration.disabled"));
						FandomConnect.INSTANCE.saveConfig();
					} else if (config.getString("towny.integration_enabled").equals("false")) {
						config.set("towny.integration_enabled", "true");
						FandomConnect.INSTANCE.saveConfig();
						sender.sendMessage(languageFile.getString("towny.integration.enabled"));
					} else {
						sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
					}
				} else if (args.length >= 3) {
					if (args[2].equals("town")) {
						if(args.length == 3) {
							if (config.getString("towns").equals("true")) {
								config.set("towny.towns", "false");
								sender.sendMessage(languageFile.getString("towny.town_data_sending.disabled"));
								FandomConnect.INSTANCE.saveConfig();
							} else if (config.getString("towny.towns").equals("false")) {
								config.set("towny.towns", "true");
								FandomConnect.INSTANCE.saveConfig();
								sender.sendMessage(languageFile.getString("towny.town_data_sending.enabled"));
							} else {
								sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
							}
						}
						else if (args.length == 4) {
							if(args[3].equals("page_creation")) {
								if (config.getString("towny.create_page_to_list_towns").equals("true")) {
									config.set("towny.create_page_to_list_towns", "false");
									sender.sendMessage(languageFile.getString("towny.create_page_to_list_towns.disabled"));
									FandomConnect.INSTANCE.saveConfig();
								} else if (config.getString("towny.create_page_to_list_towns").equals("false")) {
									config.set("towny.create_page_to_list_towns", "true");
									FandomConnect.INSTANCE.saveConfig();
									sender.sendMessage(languageFile.getString("towny.create_page_to_list_towns.enabled"));
								} else {
									sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
								}
							}
							if(args[3].equals("infobox_creation")) {
								if (config.getString("towny.create_infobox_for_towns").equals("true")) {
									config.set("towny.create_infobox_for_towns", "false");
									sender.sendMessage(languageFile.getString("towny.create_infobox_for_towns.disabled"));
									FandomConnect.INSTANCE.saveConfig();
								} else if (config.getString("towny.create_infobox_for_towns").equals("false")) {
									config.set("towny.create_infobox_for_towns", "true");
									FandomConnect.INSTANCE.saveConfig();
									sender.sendMessage(languageFile.getString("towny.create_infobox_for_towns.enabled"));
								} else {
									sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
								}
							}
						}
					} else if (args[2].equals("nation")) {
						if(args.length == 3) {
							if (config.getString("towny.nations").equals("true")) {
								config.set("towny.nations", "false");
								sender.sendMessage(languageFile.getString("towny.nation_data_sending.disabled"));
								FandomConnect.INSTANCE.saveConfig();
							} else if (config.getString("towny.nations").equals("false")) {
								config.set("towny.nations", "true");
								FandomConnect.INSTANCE.saveConfig();
								sender.sendMessage(languageFile.getString("towny.nation_data_sending.enabled"));
							} else {
								sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
							}
						}
						else if (args.length == 4) {
							if(args[3].equals("page_creation")) {
								if (config.getString("towny.create_page_to_list_nations").equals("true")) {
									config.set("towny.create_page_to_list_nations", "false");
									sender.sendMessage(languageFile.getString("towny.create_page_to_list_nations.disabled"));
									FandomConnect.INSTANCE.saveConfig();
								} else if (config.getString("towny.create_page_to_list_nations").equals("false")) {
									config.set("towny.create_page_to_list_nations", "true");
									FandomConnect.INSTANCE.saveConfig();
									sender.sendMessage(languageFile.getString("towny.create_page_to_list_nations.enabled"));
								} else {
									sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
								}
							}
							if(args[3].equals("infobox_creation")) {
								if (config.getString("towny.create_infobox_for_nations").equals("true")) {
									config.set("towny.create_infobox_for_nations", "false");
									sender.sendMessage(languageFile.getString("towny.create_infobox_for_nations.disabled"));
									FandomConnect.INSTANCE.saveConfig();
								} else if (config.getString("towny.create_infobox_for_nations").equals("false")) {
									config.set("towny.create_infobox_for_nations", "true");
									FandomConnect.INSTANCE.saveConfig();
									sender.sendMessage(languageFile.getString("towny.create_infobox_for_nations.enabled"));
								} else {
									sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
								}
							}
						}
					}
				}
				if (args.length == 4) {
					if(args[3].equals("page_creation")) {
						if (args[2].equals("town")) {
							if (config.getString("towny.create_page_to_list_towns").equals("true")) {
								config.set("towny.create_page_to_list_towns", "false");
								sender.sendMessage(languageFile.getString("towny.create_page_to_list_towns.disabled"));
								FandomConnect.INSTANCE.saveConfig();
							} else if (config.getString("towny.create_page_to_list_towns").equals("false")) {
								config.set("towny.create_page_to_list_towns", "true");
								FandomConnect.INSTANCE.saveConfig();
								sender.sendMessage(languageFile.getString("towny.create_page_to_list_towns.enabled"));
							} else {
								sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
							}
						} else if (args[2].equals("nation")) {
							if (config.getString("towny.create_page_to_list_nations").equals("true")) {
								config.set("towny.create_page_to_list_nations", "false");
								sender.sendMessage(languageFile.getString("towny.create_page_to_list_nations.disabled"));
								FandomConnect.INSTANCE.saveConfig();
							} else if (config.getString("towny.create_page_to_list_nations").equals("false")) {
								config.set("towny.create_page_to_list_nations", "true");
								FandomConnect.INSTANCE.saveConfig();
								sender.sendMessage(languageFile.getString("towny.create_page_to_list_nations.enabled"));
							} else {
								sender.sendMessage(languageFile.getString("errors.neither_true_or_false"));
							}
						}
					}
				}
			}
		} else if (args[0].equals("sync")) {

			/*if (args[1].equals("all")) {

			} else if (args[1].equals("towns")) {

			} else if (args[1].equals("nations")) {

			} else*/ if (args[1].equals("players")) {
				if (config.getString("rpcard.integration_enabled").equals("true")) {
					syncPlayers(config);
				}
			}
		}
		return false;
	}

	public static void syncPlayers(FileConfiguration config) {
		FileConfiguration languageFile = FandomConnect.INSTANCE.getlanguageConfig();
		Wiki wiki = FandomConnect.INSTANCE.getWiki();
		wiki.login(config.getString("login.username"), config.getString("login.password"));
		File folder = new File(RpCard.INSTANCE.getDataFolder(), "/profiles");
		final ProfileSerializationManager profileSerializationManager = RpCard.INSTANCE.getProfileSerializationManager();


		for (File file: folder.listFiles()) {
			final String json = FileUtils.loadContent(file);
			final Profile profile = profileSerializationManager.deserialize(json);
			String modele_base_name;
			if (config.getString("language").equals("fr_FR")) {
				modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Modèle:", "");
			} else {
				modele_base_name = languageFile.getString("list_management.title.infobox_players").replaceAll("Template:", "");
			}

			String infobox = "{{" + modele_base_name + languageFile.getString("infobox.players");
			if(config.getString("rpcard.integration_enabled").equals("true")) {
				if (paramEnabled("rp_name")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.rp_name"), profile.getNomRp());
				if (paramEnabled("title1")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.title1"), profile.getPlayername());
				if (paramEnabled("age")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.age"), String.valueOf(profile.getAge()));
				if (config.getString("language").equals("fr_FR")) {
					if (paramEnabled("dead_or_alive")) {
						infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.dead_or_alive"), profile.getDeadoralive());
					}
				} else {
					if (paramEnabled("dead_or_alive")) {
						infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.dead_or_alive"), translator(profile.getDeadoralive()));
					}
				}
				if (paramEnabled("titles")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.titles"), profile.getTitre());
				if (paramEnabled("religion")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.religion"), profile.getReligion());
				if (paramEnabled("jobs")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.jobs"), profile.getProfession());
				if (paramEnabled("race")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.race"), profile.getRace());
			}
			else {
				if (paramEnabled("rp_name")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.rp_name"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("title1")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.title1"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("age")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.age"), languageFile.getString("infobox.empty_value"));
				if (config.getString("language").equals("fr_FR")) {
					if (paramEnabled("dead_or_alive")) {
						infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.dead_or_alive"), "Inconnu");
					}
				} else {
					if (paramEnabled("dead_or_alive")) {
						infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.dead_or_alive"), "Unkown");
					}
				}
				if (paramEnabled("titles")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.titles"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("religion")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.religion"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("jobs")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.jobs"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("race")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.race"), languageFile.getString("infobox.empty_value"));
			}
			if (config.getString("towny.integration_enabled").equals("true") && (TownyUniverse.getInstance().getResident(profile.getUuid()) != null)) {
				if (TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() != null) {
					if (paramEnabled("town")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.town"), TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName());
					if (paramEnabled("nation")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.nation"), TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull().getName());
				} else {
					if (TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull() != null && TownyUniverse.getInstance().getResident(profile.getUuid()).getNationOrNull() == null) {
						if (paramEnabled("town")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.town"), TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName());
					}
					else {
						if (paramEnabled("town")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.town"), languageFile.getString("infobox.empty_value"));
					}
					if (paramEnabled("nation")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.nation"), languageFile.getString("infobox.empty_value"));
				}
				if (TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull() != null) {
					if (paramEnabled("money")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.money"), String.valueOf(TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull().getHoldingBalance()));
				}
				else {
					if (paramEnabled("money")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.money"), languageFile.getString("infobox.empty_value"));
				}
			}
			else {
				if (paramEnabled("town")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.town"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("nation")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.nation"), languageFile.getString("infobox.empty_value"));
				if (paramEnabled("money")) infobox = infobox.replaceAll("Exemple" + config.getString("infobox_composition.players.money"), languageFile.getString("infobox.empty_value"));
			}
			if (!wiki.exists(profile.getPlayername()) || !wiki.getTemplatesOnPage(profile.getPlayername()).contains(languageFile.getString("list_management.title.infobox_players"))) {
				wiki.addText(profile.getPlayername(), infobox, languageFile.getString("list_management.reason.added_something.players"), false);
			} else {
				int start;
				int end;
				String pageText = wiki.getPageText(profile.getPlayername());

				if(config.getString("rpcard.integration_enabled").equals("true")) {

					start = pageText.indexOf("|" + getParam("1") + "=");
					end = pageText.indexOf("|" + getParam("2") + "=");
					pageText = new StringBuffer(pageText).replace(start, end, "|" + getParam("1") + "=" + getValueFromParam("1", profile)).toString();

					start = pageText.indexOf("|" + getParam("2") + "=");
					end = pageText.indexOf("|" + getParam("3") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("2") + "= " + getValueFromParam("2", profile)).toString();

					if (config.getString("language").equals("fr_FR")) {
						pageText = pageText.replaceAll("dead_or_alive=Inconnu", "dead_or_alive=" + profile.getDeadoralive());
						pageText = pageText.replaceAll("dead_or_alive=Vivant", "dead_or_alive=" + profile.getDeadoralive());
						pageText = pageText.replaceAll("dead_or_alive=Mort", "dead_or_alive=" + profile.getDeadoralive());
					} else {
						pageText = pageText.replaceAll("dead_or_alive=Unkown", "dead_or_alive=" + translator(profile.getDeadoralive()));
						pageText = pageText.replaceAll("dead_or_alive=Alive", "dead_or_alive=" + translator(profile.getDeadoralive()));
						pageText = pageText.replaceAll("dead_or_alive=Dead", "dead_or_alive=" + translator(profile.getDeadoralive()));
					}

					start = pageText.indexOf("|" + getParam("3") + "=");
					end = pageText.indexOf("|" + getParam("4") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("3") + "= " + getValueFromParam("3", profile)).toString();

					start = pageText.indexOf("|" + getParam("4") + "=");
					end = pageText.indexOf("|" + getParam("5") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("4") + "= " + getValueFromParam("4", profile)).toString();

					start = pageText.indexOf("|" + getParam("5") + "=");
					end = pageText.indexOf("|" + getParam("6") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("5") + "= " + getValueFromParam("5", profile)).toString();

					start = pageText.indexOf("|" + getParam("6") + "=");
					end = pageText.indexOf("|" + getParam("7") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("6") + "= " + getValueFromParam("6", profile)).toString();

					start = pageText.indexOf("|" + getParam("7") + "=");
					end = pageText.indexOf("|" + getParam("8") + "=");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("7") + "= " + getValueFromParam("7", profile)).toString();

				}

				if(config.getString("towny.integration_enabled").equals("true")) {
					if (TownyUniverse.getInstance().getResident(profile.getUuid()) != null) {
						start = pageText.indexOf("|" + getParam("8") + "=");
						end = pageText.indexOf("|money=");
						pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("8") + "= " + getValueFromParam("8", profile)).toString();
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
							pageText =new StringBuffer(pageText).replace(start, end, "|town=" + TownyUniverse.getInstance().getResident(profile.getUuid()).getTownOrNull().getName()).toString();
						}
						if (TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull() != null) {
							start = pageText.indexOf("|money=");
							end = pageText.indexOf("|town=");
							pageText = new StringBuffer(pageText).replace(start, end, "|money=" + String.valueOf(TownyUniverse.getInstance().getResident(profile.getUuid()).getAccountOrNull().getHoldingBalance())).toString();
						}
					}
				}
				else {
					start = pageText.indexOf("|" + getParam("8") + "=");
					end = pageText.indexOf("}}");
					pageText =new StringBuffer(pageText).replace(start, end, "|" + getParam("8") + "= " + getValueFromParam("8", profile)).toString();
				}
				wiki.edit(profile.getPlayername(), pageText, "");
			}
			if (!(wiki.getPageText(languageFile.getString("list_management.title.players")).contains(profile.getPlayername())) && config.getString("rpcard.create_page_to_list_players").equals("true")) {
				wiki.addText(languageFile.getString("list_management.title.players"), "\r\n" +
						"\r\n" +
						"[[" + profile.getPlayername() + "]]", languageFile.getString("list_management.reason.added_something.players"), false);
			}
		}
	}

	public static String translator(String toTranslate) {
		if(toTranslate.equals("Inconnu")) {
			return "Unkown";
		} else if(toTranslate.equals("Vivant")) {
			return "Alive";
		} else if(toTranslate.equals("Mort")) {
			return "Dead";
		} else {
			return null;
		}
	}

	public static boolean paramEnabled(String param) {
		if (Integer.valueOf(FandomConnect.INSTANCE.getConfig().getString("infobox_composition.players." + param)) >= 1 || Integer.valueOf(FandomConnect.INSTANCE.getConfig().getString("infobox_composition.players." + param)) == 01 ) {
			return true;
		} else {
			return false;
		}

	}

	public static String getParam(String param_id) {
		FileConfiguration config = FandomConnect.INSTANCE.getConfig();
		if(config.getString("infobox_composition.players.rp_name").equals(param_id)) {
			return "rp_name";
		} else if(config.getString("infobox_composition.players.age").equals(param_id.toString())) {
			return "age";
		} else if(config.getString("infobox_composition.players.title1").equals(param_id.toString())) {
			return "title1";
		} else if(config.getString("infobox_composition.players.dead_or_alive").equals(param_id.toString())) {
			return "dead_or_alive";
		} else if(config.getString("infobox_composition.players.titles").equals(param_id.toString())) {
			return "titles";
		} else if(config.getString("infobox_composition.players.religion").equals(param_id.toString())) {
			return "religion";
		} else if(config.getString("infobox_composition.players.jobs").equals(param_id.toString())) {
			return "jobs";
		} else if(config.getString("infobox_composition.players.race").equals(param_id.toString())) {
			return "race";
		} else {
			return "}}";
		}
	}

	public static String getValueFromParam(String param_id, Profile profile) {
		FileConfiguration config = FandomConnect.INSTANCE.getConfig();
		if(config.getString("infobox_composition.players.rp_name").equals(param_id)) {
			return profile.getNomRp();
		} else if(config.getString("infobox_composition.players.age").equals(param_id.toString())) {
			return String.valueOf(profile.getAge());
		} else if(config.getString("infobox_composition.players.title1").equals(param_id.toString())) {
			return profile.getPlayername();
		} else if(config.getString("infobox_composition.players.dead_or_alive").equals(param_id.toString())) {
			return profile.getDeadoralive();
		} else if(config.getString("infobox_composition.players.titles").equals(param_id.toString())) {
			return profile.getTitre();
		} else if(config.getString("infobox_composition.players.religion").equals(param_id.toString())) {
			return profile.getReligion();
		} else if(config.getString("infobox_composition.players.jobs").equals(param_id.toString())) {
			return profile.getProfession();
		} else if(config.getString("infobox_composition.players.race").equals(param_id.toString())) {
			return profile.getRace();
		} else {
			return "}}";
		}
	}

}