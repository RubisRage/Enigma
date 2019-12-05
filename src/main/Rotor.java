package main;

class Rotor{

    private Disk rightDisk, leftDisk;
    private int rotations;

    Rotor(int[] wiring, int initialPosition){
        rightDisk = new Disk("Rotor1[RightDisk]");
        leftDisk = new Disk("Rotor1[LeftDisk]");
        rightDisk.linkWiring(leftDisk, wiring, initialPosition);
        rotations = 0;
    }

    void rotate(){
        rightDisk.rotate();
        leftDisk.rotate();
        rotations++;
        if(rotations == 27) rotations = 0;
    }


    int frontEncode(int letter){
        return leftDisk.getOutput(rightDisk.getInput(letter));
    }

    int backEncode(int letter){
        return rightDisk.getOutput(leftDisk.getInput(letter));
    }

    int getRotations(){
        return rotations;
    }
}
