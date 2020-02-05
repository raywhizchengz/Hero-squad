import models.Hero;
import models.Squad;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
import static spark.route.HttpMethod.post;
import static spark.Spark.*;

public class App {
    public static <Squads> void main(String[] args) {
        ProcessBuilder processor = new ProcessBuilder();
        int port;

        if (processor.environment().get("PORT") != null) {
            port = Integer.parseInt(processor.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);
        staticFileLocation("/public");


        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Hero> Hero = Hero.getAll();
            model.put("Hero", Hero);
            model.put("Squads", Squad.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
        get("/hero", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Hero> Hero = Hero.getAll();
            model.put("Hero", Hero);
            model.put("Squads", Squad.getAllSquads());
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squads", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Squad> Squad = Squad.getAll();
            model.put("Squad", Squad);
            model.put("Heroes", Hero.getAll());
            return new ModelAndView(model, "herosquads.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadId = Integer.parseInt(req.queryParams("id"));
            Squads availableSquads = Squad.getSquadById(squadId);
            model.put("availableSquads", availableSquads);
            return new ModelAndView(model, "details.hbs");
        }, new HandlebarsTemplateEngine());
        get("/posts/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.getAllSquads());
            return new ModelAndView(model, "heroTable.hbs");
        }, new HandlebarsTemplateEngine());
        get("/posts/squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", Squad.getAllSquads());
            return new ModelAndView(model, "groupsquads.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroInfo/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int HeroId = Integer.parseInt(req.queryParams("id"));
            Hero availableHero = Hero.getHeroById(HeroId);
            model.put("availableHero", availableHero);
            model.put("squads", Squad.getAllSquads());
            return new ModelAndView(model, "heroInfo.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squad-Id/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int squadId = Integer.parseInt(req.queryParams("id"));
            Squad availableSquad = Squad.getSquadById(squadId);
            model.put("availableSquad", availableSquad);
            model.put("Squads", Squad.getAllSquads());
            return new ModelAndView(model, "squadId.hbs");
        }, new HandlebarsTemplateEngine());
        get("/hero/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int editHeroId = Integer.parseInt(req.queryParams(""));
            Hero editHero = Hero.getHeroById(editHeroId);
            model.put("editHero", editHero);
            return new ModelAndView(model, "updateHero.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squad/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int editSquadId = Integer.parseInt(req.queryParams("id"));
            Hero editSquad = Hero.getHeroById(editSquadId);
            model.put("editSquad", editSquad);
            return new ModelAndView(model, "updateSquad.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squads/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int deleteSquadId = Integer.parseInt(req.queryParams("id"));
            Squad deleteSquad = Squad.getSquadById(deleteSquadId);
            deleteSquad.deleteSquadById();
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroes/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int deleteHeroId = Integer.parseInt(req.queryParams("id"));
            Hero deleteHero = Hero.getHeroById(deleteHeroId);
            deleteHero.deleteHeroById();
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Hero.clearAll();
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        get("/squads/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Squad.clearAll();
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        post("/posts/squads/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            int Size = Integer.parseInt(req.queryParams("size"));
            Squad mySquad = new Squad(name, cause, Size);
            model.put("squads", mySquad.getAllSquads());
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        post("/posts/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String ability = req.queryParams("ability");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int idSquad = Integer.parseInt(req.queryParams("idSquad"));
            Hero hero = new Hero(name, age, ability, weakness,  idSquad);
            ArrayList<Hero> heroes = Hero.getAll();
            model.put("heroes", heroes);
            model.put("squads", Squad.getAllSquads());
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        post("/squads/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            int maxSize = Integer.parseInt(req.queryParams("maxSize"));
            Squad editSquad = new Squad(name, cause, maxSize);
            editSquad.update(name, cause, maxSize);
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
        post("/heroes/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String mutant = req.queryParams("Mutant");
            String ability = req.queryParams("ability");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadId = Integer.parseInt(req.queryParams("squadId"));
            Hero editHero = new Hero(mutant, age, ability, weakness, squadId);
            editHero.update(mutant, age, ability, weakness);
            return new ModelAndView(model, "confirm.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
