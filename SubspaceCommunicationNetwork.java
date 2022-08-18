import java.util.*;

public class SubspaceCommunicationNetwork {

    private List<SolarSystem> solarSystems;

    /**
     * Perform initializations regarding your implementation if necessary
     * @param solarSystems a list of SolarSystem objects
     */
    public SubspaceCommunicationNetwork(List<SolarSystem> solarSystems) {
        // TODO: YOUR CODE HERE
        this.solarSystems = solarSystems;
    }
    Double calculateCost(Planet planet1, Planet planet2){

        double avr = (double)(planet1.getTechnologyLevel()+planet2.getTechnologyLevel())/2d;
        return Constants.SUBSPACE_COMMUNICATION_CONSTANT/avr;

    }

    Planet maxTechLevel(SolarSystem solarSystem){

        Planet newPlanet = solarSystem.getPlanets().get(0);
        for (Planet planet : solarSystem.getPlanets())
            if (planet.getTechnologyLevel()>newPlanet.getTechnologyLevel())
                newPlanet=planet;

        return newPlanet;

    }

    /**
     * Using the solar systems of the network, generates a list of HyperChannel objects that constitute the minimum cost communication network.
     * @return A list HyperChannel objects that constitute the minimum cost communication network.
     */
    public List<HyperChannel> getMinimumCostCommunicationNetwork() {


        List<HyperChannel> network = new ArrayList<>();
         for (int i = 0; i < solarSystems.size();i++){

            SolarSystem solarSystem1 = solarSystems.get(i);
            for (int j = i+1; j < solarSystems.size();j++){
                SolarSystem solarSystem2= solarSystems.get(j);
                Planet a = maxTechLevel(solarSystem1);
                Planet b = maxTechLevel(solarSystem2);

                if (!solarSystem1.equals(solarSystem2))
                    network.add(
                            new HyperChannel(a, b, calculateCost(a,b)));
            }
        }

         network.sort(Comparator.comparing(HyperChannel::getWeight));

         List<HyperChannel> minimum = new ArrayList<>();

         ArrayList<String> visited = new ArrayList<>();

         for (HyperChannel hyperChannel: network){

             String from = hyperChannel.getFrom().getId();
             String to = hyperChannel.getTo().getId();

             if (visited.contains(from) && visited.contains(to))
                 continue;

             minimum.add(hyperChannel);

             visited.add(hyperChannel.getFrom().getId());
             visited.add(hyperChannel.getTo().getId());

         }




        return minimum;
    }

    public void printMinimumCostCommunicationNetwork(List<HyperChannel> network) {
        double sum = 0;
        for (HyperChannel channel : network) {
            Planet[] planets = {channel.getFrom(), channel.getTo()};
            Arrays.sort(planets);
            System.out.printf("Hyperchannel between %s - %s with cost %f", planets[0], planets[1], channel.getWeight());
            System.out.println();
            sum += channel.getWeight();
        }
        System.out.printf("The total cost of the subspace communication network is %f.", sum);
        System.out.println();
    }

}
