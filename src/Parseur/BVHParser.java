package Parseur;
/* Parseur.BVHParser extended for creating a DGP instance
 *
 * Antonio Mucherino
 *
 *   August 2016 - work started
 * December 2016 - environment is optional, frame is an integer
 *
 */

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

import Jama.*;  //Matrix;

public class BVHParser {
    private static final String HIERARCHY = "HIERARCHY";
    private static final String MOTION = "MOTION";
    private static final String ROOT = "ROOT";
    private static final String JOINT = "JOINT";
    private static final String OFFSET = "OFFSET";
    private static final String END_0 = "End";
    private static final String END_1 = "Site";
    private static final String PARENT_CLOSE = "}";
    private static final int jointsEnvFrameSkip = 46;
    private static final List<String> jointsEnv = Arrays.asList();  // previous experiment: RT, LT

    private String state;

    private String lastToken = "";
    private Joint poppedJoint = null;

    private ArrayList<Joint> jointsList = new ArrayList<Joint>();
    private Stack<Joint> jointsStack = new Stack<Joint>();
    private int jointNo = 1;

    private ArrayList<Joint> uniqueJoints = new ArrayList<Joint>();
    private ArrayList<Joint> leafJoints = new ArrayList<Joint>();
    private int connectionMatrix[][];

    Joint joint = null;
    private double currentX = 0;
    private double currentY = 0;
    private double currentZ = 0;

    private boolean chainEnd = false;
    private boolean useEndSite = false;

    private ArrayList<StringBuffer> motionCoords = new ArrayList<StringBuffer>();

    private double frameTime;
    private int countFrames = 0;
    private ArrayList<Joint> sortedJointEnvList = new ArrayList<Joint>();
    private ArrayList<Integer> sortedJointEnvFrames = new ArrayList<Integer>();

    private int countEnv = 0;
    private int countAnim = 0;
    private String skeletonGraph = "";
    private String skeletonFrame1 = "";
    private String environment = "";
    private String envGraph = "";
    private String animationCoords = "";
    private String mesh = "";
    private String instance = "";

    public BVHParser(String nomDuFichier, String useEndSite) {
        if (useEndSite.equalsIgnoreCase("y")) this.useEndSite = true;
        creationMatrice(nomDuFichier);
        printFiles(nomDuFichier);
    }

    private void creationMatrice(String nomDuFichier) {
        EasyReader reader = new EasyReader(nomDuFichier);
        String ligne = "";
        String [] splitted;

        while (!reader.eof()) {
            //trim : enleve les espace en debut de ligne
            ligne = (reader.readString()).trim();
            if (!ligne.equals("")) {
                splitted = splitString(ligne);
                if (splitted[0].equals(HIERARCHY)) {
                    state = HIERARCHY;
                }
                else if (splitted[0].equals(MOTION)) {
                    state = MOTION;
                    reader.readString();    // Skip Frames
                    ligne = reader.readString();
                    splitted = splitString(ligne);
                    frameTime = Double.parseDouble(splitted[2]);
                    System.out.println("Frame Time = " + frameTime);
                }
                else if (state.equals(HIERARCHY)) {
                    if (splitted[0].equals(ROOT) || splitted[0].equals(JOINT)) {
                        if (lastToken.equals(PARENT_CLOSE)) {
                            if (jointsStack.size() > 0) {
                                // one-step backtracking to get coordinates of predecessor
                                Joint peekJoint = jointsStack.peek();
                                jointsList.add(peekJoint);
                                currentX = peekJoint.getX();
                                currentY = peekJoint.getY();
                                currentZ = peekJoint.getZ();
                            }
                        }
                        joint = new Joint(jointNo, splitted[1]);
                    }
                    else if (splitted[0].equals(END_0) && splitted[1].equals(END_1)) {
                        // Put last joint as leaf joint
                        leafJoints.add(joint);
                        joint = new Joint(jointNo, END_0);
                        chainEnd = true;
                    }
                    else if (splitted[0].equals(OFFSET)) {
                        double offsetX = Double.parseDouble(splitted[1]);
                        double offsetY = Double.parseDouble(splitted[2]);
                        double offsetZ = Double.parseDouble(splitted[3]);

                        currentX += offsetX;
                        currentY += offsetY;
                        currentZ += offsetZ;

                        // Read offset values
                        joint.setOffsetX(offsetX);
                        joint.setOffsetY(offsetY);
                        joint.setOffsetZ(offsetZ);
                        joint.setX(currentX);
                        joint.setY(currentY);
                        joint.setZ(currentZ);

                        // Submit Parseur.Joint to collection
                        if (!joint.getNom().equals(END_0) || (useEndSite && joint.getNom().equals(END_0))) {
                            addToJointListAndIncrement(joint);
                        }
                        ;
                        jointsStack.push(joint);
                    }
                    else if (splitted[0].equals(PARENT_CLOSE)) {
                        // Pop current parent if is end of chain otherwise it will backtrack to itself
                        if (chainEnd) {
                            jointsStack.pop();
                            chainEnd = false;
                        } else {
                            if (jointsStack.size() > 0) {
                                poppedJoint = jointsStack.pop();
                                jointsList.add(poppedJoint);
                                if (jointsStack.size() > 0) {
                                    currentX = (jointsStack.peek()).getX();
                                    currentY = (jointsStack.peek()).getY();
                                    currentZ = (jointsStack.peek()).getZ();
                                }
                            }
                        }
                    }
                    lastToken = splitted[0];
                }
                else if (state.equals(MOTION)) {
                    ArrayList<Matrix> frameVertices = new ArrayList<Matrix>();
                    int currentJointNo = 0;
                    countFrames++;

                    if (countFrames == 401)
                        break;  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

                    if (countFrames % 100 == 0) System.out.println("... working on frame " + countFrames + " ...");

                    // Ignore 0-2 to make character stay in place
                    int pos = 3;

                    // Currently only supports order ZXY
                    for (int i = 3; i < splitted.length; ) {
                        double rotZ = Double.parseDouble(splitted[i]);
                        rotZ = degreesToRad(rotZ);
                        i++;
                        double rotX = Double.parseDouble(splitted[i]);    // Swap X and Y
                        rotX = degreesToRad(rotX);
                        i++;
                        double rotY = Double.parseDouble(splitted[i]);
                        rotY = degreesToRad(rotY);
                        i++;

                        double[][] coordsX = {{1, 0, 0, 0}, {0, Math.cos(rotX), -Math.sin(rotX), 0}, {0, Math.sin(rotX), Math.cos(rotX), 0}, {0, 0, 0, 1}};
                        double[][] coordsY = {{Math.cos(rotY), 0, Math.sin(rotY), 0}, {0, 1, 0, 0}, {-Math.sin(rotY), 0, Math.cos(rotY), 0}, {0, 0, 0, 1}};
                        double[][] coordsZ = {{Math.cos(rotZ), -Math.sin(rotZ), 0, 0}, {Math.sin(rotZ), Math.cos(rotZ), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                        double[][] translate = {{0, 0, 0, (uniqueJoints.get(currentJointNo)).getOffsetX()},
                                {0, 0, 0, (uniqueJoints.get(currentJointNo)).getOffsetY()},
                                {0, 0, 0, (uniqueJoints.get(currentJointNo)).getOffsetZ()},
                                {0, 0, 0, 0}};
                        Matrix matrixX = new Matrix(coordsX);
                        Matrix matrixY = new Matrix(coordsY);
                        Matrix matrixZ = new Matrix(coordsZ);
                        Matrix matrixTranslate = new Matrix(translate);
                        Matrix matrixM = matrixZ;
                        matrixM = matrixM.times(matrixX);
                        matrixM = matrixM.times(matrixY);
                        matrixM = matrixM.plus(matrixTranslate);
                        frameVertices.add(matrixM);
                        currentJointNo++;
                    }
                    ;

                    for (int i = 0; i < frameVertices.size(); i++) {
                        Matrix m;
                        double mult[] = {0, 0, 0, 1};
                        Matrix rightMost = new Matrix(mult, 1);
                        rightMost = rightMost.transpose();

                        Joint tempJoint = uniqueJoints.get(i);
                        Joint parents[] = tempJoint.getParents();

                        if (parents.length > 0) {
                            m = frameVertices.get(parents[0].getNombre() - 1);

                            for (int j = 1; j < parents.length; j++) {
                                m = m.times(frameVertices.get(parents[j].getNombre() - 1));
                            }
                            ;
                            m = m.times(frameVertices.get(i));
                        } else {
                            m = frameVertices.get(i);
                        }
                        ;
                        m = m.times(rightMost);

                        double results[][] = m.getArray();
                        tempJoint.setMotionX(results[0][0]);
                        tempJoint.setMotionY(results[1][0]);
                        tempJoint.setMotionZ(results[2][0]);
                        Joint p;

                        // info extracted from 1st frame only (skeleton info)
                        if (countFrames == 1) {
                            // updating skeletonFrame1
                            // String newCoords = String.format("%20.16f %20.16f %20.16f\n",tempJoint.getMotionX(),tempJoint.getMotionY(),tempJoint.getMotionZ());
                            String newCoords = String.format("%4d %4s  %20.16f %20.16f %20.16f\n",
                                    tempJoint.getNombre(), tempJoint.getShortName(),
                                    tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                            skeletonFrame1 = skeletonFrame1 + newCoords;

                            // updating skeletonGraph
                            p = tempJoint.getCloserParent();
                            if (p != null) {
                                double d = distance(p.getMotionX(), p.getMotionY(), p.getMotionZ(), tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                                String newEdge = String.format("%4d %4d %4s %4s %4d %4d %20.16f\n",
                                        p.getNombre(), tempJoint.getNombre(), p.getShortName(), tempJoint.getShortName(),
                                        countFrames, countFrames, d);
                                skeletonGraph = skeletonGraph + newEdge;
                            }
                            ;
                        }
                        ;

                        // info anout the skeleton graph that are copied over all frames (top of instance file)
                        p = tempJoint.getCloserParent();
                        if (p != null) {
                            double d = distance(p.getMotionX(), p.getMotionY(), p.getMotionZ(), tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                            String newEdge = String.format("%4d %4d %4s %4s %4d %4d %20.16f\n",
                                    (jointNo - 1) * (countFrames - 1) + p.getNombre(), (jointNo - 1) * (countFrames - 1) + tempJoint.getNombre(),
                                    p.getShortName(), tempJoint.getShortName(),
                                    countFrames, countFrames, d);
                            instance = instance + newEdge;
                        }
                        ;

                        // info extracted from environment vertices (if jointsEnv is non-empty)
                        if (jointsEnv.size() > 0 && (countFrames == 1 || countFrames % jointsEnvFrameSkip == 0)) {
                            if (doesJointBelongToEnv(tempJoint)) {
                                // updating environment
                                countEnv++;
                                String newCoords = String.format("%5d %5s %4d %20.16f %20.16f %20.16f\n",
                                        (jointNo - 1) * (countFrames - 1) + tempJoint.getNombre(), tempJoint.getShortName(), countFrames,
                                        tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                                environment = environment + newCoords;

                                // updating envGraph
                                int len = sortedJointEnvList.size();
                                int refs[] = new int[4];
                                refs[0] = len - 1;
                                refs[1] = len - 2;
                                refs[2] = len - 3;
                                refs[3] = len - 4;
                                for (int k = 0; k < 4 && k < len; k++) {
                                    if (refs[k] >= 0) {
                                        p = sortedJointEnvList.get(refs[k]);
                                        int prev_frame = sortedJointEnvFrames.get(refs[k]);
                                        double d = distance(p.getMotionX(), p.getMotionY(), p.getMotionZ(), tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                                        String newEdge = String.format("%5d %5d %5s %5s %4d %4d %20.16f\n",
                                                (jointNo - 1) * (prev_frame - 1) + p.getNombre(), (jointNo - 1) * (countFrames - 1) + tempJoint.getNombre(),
                                                p.getShortName(), tempJoint.getShortName(),
                                                prev_frame, countFrames, d);
                                        envGraph = envGraph + newEdge;
                                    }
                                    ;
                                }
                                ;

                                // saving environment vertices in appearance order
                                sortedJointEnvList.add(tempJoint);
                                sortedJointEnvFrames.add(countFrames);
                            }
                            ;
                        }
                        ;

                        // for all joints, and for all frames
                        countAnim++;
                        String newCoords = String.format("%5d %5s %4d %20.16f %20.16f %20.16f\n",
                                (jointNo - 1) * (countFrames - 1) + tempJoint.getNombre(), tempJoint.getShortName(), countFrames,
                                tempJoint.getMotionX(), tempJoint.getMotionY(), tempJoint.getMotionZ());
                        animationCoords = animationCoords + newCoords;
                    }
                    ;

                    addToList();
                } else {
                    System.out.println("ERROR IN BVH FILE!");
                }
            }
        }
    }

    ;

    private void addToJointListAndIncrement(Joint joint) {
        Joint parents[] = new Joint[jointsStack.size()];
        for (int i = 0; i < jointsStack.size(); i++) {
            Joint parentJoint = jointsStack.elementAt(i);
            parents[i] = parentJoint;
            joint.setParents(parents);
        }
        ;
        jointsList.add(joint);
        uniqueJoints.add(joint);
        this.jointNo++;
    }

    ;

    private void addToList() {
        StringBuffer cJoints = new StringBuffer();

        Joint tempJoint;
        for (int i = 0; i < uniqueJoints.size(); i++) {
            tempJoint = uniqueJoints.get(i);
            cJoints.append(tempJoint.getMotionX() + " ");
        }
        ;

        for (int i = 0; i < uniqueJoints.size(); i++) {
            tempJoint = uniqueJoints.get(i);
            cJoints.append(tempJoint.getMotionY() + " ");
        }
        ;

        for (int i = 0; i < uniqueJoints.size(); i++) {
            tempJoint = uniqueJoints.get(i);
            cJoints.append(tempJoint.getMotionZ());
            if (i < uniqueJoints.size() - 1)
                cJoints.append(" ");
        }
        ;

        motionCoords.add(cJoints);
    }

    ;

    private double degreesToRad(double degrees) {
        return (degrees * Math.PI) / 180.0;
    }

    ;

    private String[] splitString(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        String splitted[] = new String[tokenizer.countTokens()];

        for (int i = 0; i < splitted.length; i++) {
            splitted[i] = tokenizer.nextToken();
        }
        ;

        return splitted;
    }

    ;

    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d;
        d = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
        d = Math.sqrt(d);
        return d;
    }

    ;

    private boolean doesJointBelongToEnv(Joint joint) {
        int envlen = jointsEnv.size();
        boolean bool = false;

        for (int i = 0; i < envlen && !bool; i++) {
            if (joint.getShortName().equalsIgnoreCase(jointsEnv.get(i))) {
                bool = true;
            }
            ;
        }
        ;

        return bool;
    }

    ;

    private static String frameToString(int frame) {
        String str;

        if (frame >= 1000) {
            str = "F";
        } else if (frame >= 100) {
            str = "F0";
        } else if (frame >= 10) {
            str = "F00";
        } else {
            str = "F000";
        }
        ;
        str = str + frame;

        return str;
    }

    ;

    private void printFiles(String filename) {
        int len = filename.length();
        String fileSkeletonGraph;
        String fileSkeletonFrame1;
        String fileEnvGraph;
        String fileEnvironment;
        String fileAnimationCoords;
        String fileInstance;
        String fileMesh;
        String splitted[];

        String fileNoExtension = "bvh";
        if (len > 4) {
            String lastFour = filename.substring(len - 4, len);
            if (lastFour.equalsIgnoreCase(".bvh")) fileNoExtension = filename.substring(0, len - 4);
        }
        ;

        fileSkeletonGraph = fileNoExtension + "_skeletonGraph.txt";
        fileSkeletonFrame1 = fileNoExtension + "_skeletonFrame1.txt";
        fileEnvGraph = fileNoExtension + "_envGraph.txt";
        fileEnvironment = fileNoExtension + "_environment.txt";
        fileAnimationCoords = fileNoExtension + "_animationCoords.txt";
        fileInstance = fileNoExtension + ".nmr";
        fileMesh = fileNoExtension + "_mesh.txt";

        System.out.print("Printing file '" + fileSkeletonGraph + "' ... ");
        try {
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileSkeletonGraph)));
            output.println(this.skeletonGraph);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("error: file not written.");
        }
        ;
        System.out.print("done!\n");

        System.out.print("Printing file '" + fileSkeletonFrame1 + "' ... ");
        try {
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileSkeletonFrame1)));
            output.println(this.skeletonFrame1);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("error: file not written.");
        }
        ;
        System.out.print("done!\n");

        if (this.envGraph.length() > 0) {
            System.out.print("Printing file '" + fileEnvGraph + "' ... ");
            try {
                PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileEnvGraph)));
                output.println(this.envGraph);
                output.flush();
                output.close();
            } catch (IOException e) {
                System.out.println("error: file not written.");
            }
            ;
            System.out.print("done!\n");
        }
        ;

        if (this.environment.length() > 0) {
            System.out.print("Printing file '" + fileEnvironment + "' ... ");
            try {
                PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileEnvironment)));
                output.println(this.environment);
                output.flush();
                output.close();
            } catch (IOException e) {
                System.out.println("error: file not written.");
            }
            ;
            System.out.print("done!\n");
        }
        ;

        System.out.print("Printing file '" + fileAnimationCoords + "' ... ");
        try {
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileAnimationCoords)));
            output.println(this.animationCoords);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("error: file not written.");
        }
        ;
        System.out.print("done!\n");

        System.out.print("Printing file '" + fileInstance + "' ... ");
        try {
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileInstance)));
            output.println(this.instance);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("error: file not written.");
        }
        ;
        System.out.print("done!\n");

        // writing the mesh (among character and environment)
        File f1 = new File(fileAnimationCoords);
        File f2 = new File(fileEnvironment);
        if (f1.exists() && f2.exists()) {
            System.out.print("Printing file '" + fileMesh + "' ... ");

            // loading file animationCoords
            EasyReader reader1 = new EasyReader(fileAnimationCoords);
            String anim_joint[] = new String[countAnim];
            String anim_frame[] = new String[countAnim];
            double anim_x[] = new double[countAnim];
            double anim_y[] = new double[countAnim];
            double anim_z[] = new double[countAnim];
            while (!reader1.eof()) {
                String line1 = (reader1.readString()).trim();
                if (!line1.equals("")) {
                    splitted = splitString(line1);
                    int i = Integer.parseInt(splitted[0]) - 1;
                    anim_joint[i] = new String(splitted[1]);
                    anim_frame[i] = new String(splitted[2]);
                    anim_x[i] = Double.parseDouble(splitted[3]);
                    anim_y[i] = Double.parseDouble(splitted[4]);
                    anim_z[i] = Double.parseDouble(splitted[5]);
                }
                ;
            }
            ;

            // loading file environment
            EasyReader reader2 = new EasyReader(fileEnvironment);
            int env_label[] = new int[countEnv];
            String env_joint[] = new String[countEnv];
            String env_frame[] = new String[countEnv];
            double env_x[] = new double[countEnv];
            double env_y[] = new double[countEnv];
            double env_z[] = new double[countEnv];
            int j = 0;
            while (!reader2.eof()) {
                String line2 = (reader2.readString()).trim();
                if (!line2.equals("")) {
                    splitted = splitString(line2);
                    env_label[j] = Integer.parseInt(splitted[0]);
                    env_joint[j] = new String(splitted[1]);
                    env_frame[j] = new String(splitted[2]);
                    env_x[j] = Double.parseDouble(splitted[3]);
                    env_y[j] = Double.parseDouble(splitted[4]);
                    env_z[j] = Double.parseDouble(splitted[5]);
                    j++;
                }
                ;
            }
            ;

            // writing mesh
            try {
                PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileMesh)));
                for (int i = 0; i < countAnim; i++) {
                    for (j = 0; j < countEnv; j++) {
                        double dist = distance(anim_x[i], anim_y[i], anim_z[i], env_x[j], env_y[j], env_z[j]);
                        if (dist != 0.0) {
                            String newline = String.format("%5d %5d %5s %5s %5s %5s %20.16f", i + 1, env_label[j], anim_joint[i], env_joint[j], anim_frame[i], env_frame[j], dist);
                            output.println(newline);
                        }
                        ;
                    }
                    ;
                }
                ;
                output.flush();
                output.close();
            } catch (IOException e) {
                System.out.println("error: file not written.");
            }
            ;
            System.out.print("done!\n");
        }
        ;
    }

    ;

    public static void main(String args[]) {
        String useEnd;
        if (args.length > 1) {
            useEnd = args[1];
        } else {
            useEnd = "n";
        }
        ;
        BVHParser parser = new BVHParser(args[0], useEnd);
        Parse parse = new Parse();
        parse.splitChaine();


    }

    ;
};

