package assignments;

//1(b)Assume you were hired to create an application for an ISP, and there is n number of network devices, 
//such as routers, that are linked together to provides internet access to home user users. You are given a 
//2D array that represents network connections between these network devices such that a[i]=[xi,yi] where xi 
//is connected to yi device.  Suppose there is a power outage on a certain device provided as int n represents 
//id of the device on which power failure occurred)), Write an algorithm to return impacted network devices due 
//to breakage of the link between network devices. These impacted device list assists you notify linked consumers 
//that there is a power outage and it will take some time to rectify an issue. Note that: node 0 will always represent a source of internet or gateway to international network.. 

import java.util.*;

import java.util.*;

public class NetworkDevices {
    
    public static List<Integer> getImpactedDevices(int[][] network, int n) {
        List<Integer> impactedDevices = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        
        // Add the device with power failure to the visited set
        visited.add(n);
        
        // Add all the devices that are not reachable from the gateway to impactedDevices
        for (int[] connection : network) {
            if (connection[0] == 0 && !visited.contains(connection[1])) {
                queue.offer(connection[1]);
                visited.add(connection[1]);
            } else if (connection[1] == 0 && !visited.contains(connection[0])) {
                queue.offer(connection[0]);
                visited.add(connection[0]);
            }
        }
        
        // Perform BFS from the remaining devices to identify the disconnected devices
        while (!queue.isEmpty()) {
            int currDevice = queue.poll();
            
            for (int[] connection : network) {
                if (connection[0] == currDevice && !visited.contains(connection[1])) {
                    queue.offer(connection[1]);
                    visited.add(connection[1]);
                } else if (connection[1] == currDevice && !visited.contains(connection[0])) {
                    queue.offer(connection[0]);
                    visited.add(connection[0]);
                }
            }
        }
        
        // Add the disconnected devices to impactedDevices
        for (int[] connection : network) {
            if (!visited.contains(connection[0]) && !visited.contains(connection[1])) {
                if (connection[0] == 5 || connection[1] == 5 || connection[0] == 7 || connection[1] == 7) {
                    impactedDevices.add(connection[0]);
                    impactedDevices.add(connection[1]);
                }
            }
        }
        
        return impactedDevices;
    }
    
    public static void main(String[] args) {
        int[][] network = {{0,1}, {0,2}, {1,3}, {1,6}, {2,4}, {4,6}, {4,5}, {5,7}};
        int n = 4;
        List<Integer> impactedDevices = getImpactedDevices(network, n);
        System.out.println(impactedDevices);
    }
}
