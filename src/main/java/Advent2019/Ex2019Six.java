package Advent2019;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex2019Six extends ExceriseBase {

    private static final Pattern INPUT_REGEX = Pattern.compile("([a-zA-Z0-9]+)\\)([a-zA-Z0-9]+)");

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        final HashMap<String, Planet> planetsById = new HashMap<>();

        lines.stream()
                .map(this::processLine)
                .forEach(line -> orbitPlanet(line, planetsById));

        Planet rootPlanet = planetsById.values().stream()
                .filter(planet -> planet.getDirectlyOrbits() == null)
                .findFirst().get();

        System.out.println(rootPlanet.sumAllOrbits(0));

    }

    public void processB(String path) throws IOException {
        List<String> lines = readFile(path);

        final HashMap<String, Planet> planetsById = new HashMap<>();

        lines.stream()
                .map(this::processLine)
                .forEach(line -> orbitPlanet(line, planetsById));

        Planet youPlanet = planetsById.get("YOU");
        Planet sanPlanet = planetsById.get("SAN");

        Map<Planet, Integer> youDistances = youPlanet.generateDistanceToPlanets();
        Map<Planet, Integer> sanDistances = sanPlanet.generateDistanceToPlanets();

        youDistances.keySet().retainAll(sanDistances.keySet());

        Integer min = youDistances.entrySet().stream()
                .mapToInt((entry) -> entry.getValue() + sanDistances.get(entry.getKey()) - 2)
                .boxed()
                .min(Integer::compareTo)
                .get();

        System.out.println(min);

    }

    private Planet getOrMakePlanet(HashMap<String, Planet> planets, String id) {
        return planets.computeIfAbsent(id, Planet::new);
    }

    private void orbitPlanet(MatchResult result, HashMap<String, Planet> planetsById) {
        Planet orbitedPlanet = getOrMakePlanet(planetsById, result.group(1));
        Planet orbitingPlanet = getOrMakePlanet(planetsById, result.group(2));

        orbitingPlanet.setDirectlyOrbits(orbitedPlanet);
        orbitedPlanet.addOrbitedBy(orbitingPlanet);
    }

    private MatchResult processLine(String line) {
        Matcher matcher = INPUT_REGEX.matcher(line);
        matcher.find();
        return matcher.toMatchResult();
    }

    private class Planet {

        private String id;
        private Planet directlyOrbits;
        private List<Planet> orbitedBy = new ArrayList<>();

        public Planet(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public Planet getDirectlyOrbits() {
            return directlyOrbits;
        }

        public List<Planet> getOrbitedBy() {
            return orbitedBy;
        }

        public void setDirectlyOrbits(Planet directlyOrbits) {
            this.directlyOrbits = directlyOrbits;
        }

        public void addOrbitedBy(Planet orbitedBy) {
            this.orbitedBy.add(orbitedBy);
        }

        public int sumAllOrbits(int distanceFromRoot) {
            int sumOrbits = sumOrbit(distanceFromRoot);

            for (Planet orbitedPlanet : orbitedBy) {
                sumOrbits += orbitedPlanet.sumAllOrbits(distanceFromRoot + 1);
            }

            return sumOrbits;
        }

        public int sumOrbit(int distanceFromRoot) {
            return distanceFromRoot;
        }

        public Map<Planet, Integer> generateDistanceToPlanets() {
            Map<Planet, Integer> planetDistances = new HashMap<>();
            Planet traversedPlanet = directlyOrbits;
            int distanceToPlanet = 1;
            while (traversedPlanet != null) {
                planetDistances.put(traversedPlanet, distanceToPlanet);
                traversedPlanet = traversedPlanet.directlyOrbits;
                distanceToPlanet++;
            }
            return planetDistances;
        }
    }

}
