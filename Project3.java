import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Project3 {

    // generate all 6*n rotations
    public static Box[] createAllRotations(Box[] boxes) {
        int n = boxes.length;
        Box[] rotations = new Box[6 * n];

        int index = 0;
        for (Box box : boxes) {
            int w = box.width, d = box.depth, h = box.height;
            rotations[index++] = new Box(w, d, h);
            rotations[index++] = new Box(w, h, d);
            rotations[index++] = new Box(d, w, h);
            rotations[index++] = new Box(d, h, w);
            rotations[index++] = new Box(h, w, d);
            rotations[index++] = new Box(h, d, w);
        }
        return rotations;
    }

    public static int mackStackLIS(Box[] boxes) {

        long startTime = System.nanoTime();

        Box[] allRotations = createAllRotations(boxes);
        int n = allRotations.length;
        System.out.println("allRotations.length : " + allRotations.length);

        Arrays.sort(allRotations, new Comparator<Box>() {
            public int compare(Box b1, Box b2) {
                return b2.width - b1.width;
            }
        });

        // for (int i = 0; i < n; i++) {
        // System.out.println(
        // "Width : " + allRotations[i].width + " Depth : " + allRotations[i].depth + "
        // Height : "
        // + allRotations[i].height);
        // }

        int maxHeight[] = new int[n];
        for (int i = 0; i < n; i++) {
            maxHeight[i] = allRotations[i].height;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (allRotations[j].depth > allRotations[i].depth) {
                    maxHeight[i] = Math.max(maxHeight[i], maxHeight[j] + allRotations[i].height);
                }
            }
        }

        int max = 0;
        for (int height : maxHeight) {
            max = Math.max(height, max);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Duration is : " + duration);
        return max;
    }

    public static Box[] generateRandomBoxes(int size) {
        Box[] boxes = new Box[size];
        int index = 0;
        while (index < size) {
            Random r = new Random();
            int width = r.nextInt(10) + 1;
            int depth = r.nextInt(10) + 1;
            int height = r.nextInt(10) + 1;
            Box box = new Box(width, depth, height);
            boxes[index++] = box;
        }

        return boxes;
    }

    public static void main(String args[]) {
        int testArr[] = { 10, 100, 1000, 10000 };
        for (int t : testArr) {
            Box[] boxes = generateRandomBoxes(t);
            int maxHeight = mackStackLIS(boxes);
            System.out.println("The maximum possible stack height is : " + maxHeight);
        }
    }
}

class Box {
    int width, depth, height;

    Box(int width, int depth, int height) {
        this.width = width;
        this.depth = depth;
        this.height = height;
    }
}