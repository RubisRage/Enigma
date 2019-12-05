package main;


import java.awt.image.renderable.RenderableImageOp;

class Enigma {

    private static int[][] pairs = new int[][]{{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24},
            {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25}};
    private static final int[]  SWISSK_I = new int[]{15, 4, 25, 20, 14, 7, 23, 18, 2, 21,  5, 12, 19,  1,  6, 11, 17,
            8, 13, 16,  9, 22,  0, 24,  3, 10};
    private static final int[] SWISSK_II = new int[]{15, 4, 25, 20, 14, 7, 23, 18, 2, 21,  5, 12, 19,  1,  6, 11, 17, 8,
            13, 16,  9, 22,  0, 24,  3, 10};
    private static final int[] SWISSK_III = new int[]{4, 7, 17, 21, 23, 6, 0, 14, 1, 16, 20, 18,  8, 12, 25,  5, 11, 24,
            13, 22, 10, 19, 15,  3,  9, 2};

    private Rotor first, second, third;
    private Reflector reflector;

    private int[] wiring1, wiring2, wiring3, init = new int[3];

    Enigma(){
        reflector = new Reflector(pairs);
    }

    Enigma(int[] first_wiring, int first_position, int[] second_wiring, int second_position, int[] third_wiring,
           int third_position, int[][] pairs){
        first = new Rotor(first_wiring, first_position);
        wiring1 = first_wiring;
        init[0] = first_position;
        second = new Rotor(second_wiring, second_position);
        wiring2 = second_wiring;
        init[1] = second_position;
        third = new Rotor(third_wiring, third_position);
        wiring3 = third_wiring;
        init[2] = third_position;
        reflector = new Reflector(pairs);
    }

    String encodeSentence(String s) throws NullPointerException{
        StringBuilder encoded = new StringBuilder();

        for(int i = 0; i < s.length(); i++){
            int numberedLetter = letterToNum(s.charAt(i));

            if(numberedLetter == -1) return "String no válida";
            else if(letterToNum(s.charAt(i)) == 26)encoded.append(" ");
            else {
                try{
                    encoded.append(encode(letterToNum(s.charAt(i))));
                } catch(NullPointerException e){
                    throw  e;
                }
            }
        }

        return encoded.toString();
    }

    private char encode(int letter) throws NullPointerException{
        int front = 0;
        try{
            front = reflector.findPair(third.frontEncode(second.frontEncode(first.frontEncode(letter))));
        } catch(NullPointerException e){
            throw e;
        }
        char encoded = numToLetter(first.backEncode(second.backEncode(third.backEncode(front))));

        first.rotate();
        if(first.getRotations() == 26){
            second.rotate();
        }
        if(second.getRotations() == 26){
            third.rotate();
        }

        return encoded;
    }

    void setWirings(String first_wiring, int first_position, String second_wiring, int second_position, String third_wiring,
                    int third_position){
        switch (first_wiring) {
            case "SWISSK I":
                first = new Rotor(SWISSK_I, first_position);
                wiring1 = SWISSK_II;
                break;
            case "SWISSK II":
                first = new Rotor(SWISSK_II, first_position);
                wiring1 = SWISSK_II;
                break;
            default:
                first = new Rotor(SWISSK_III, first_position);
                wiring1 = SWISSK_III;
                break;
        }

        init[0] = first_position;

        switch (second_wiring) {
            case "SWISSK I":
                second = new Rotor(SWISSK_I, first_position);
                wiring2 = SWISSK_II;
                break;
            case "SWISSK II":
                second = new Rotor(SWISSK_II, first_position);
                wiring2 = SWISSK_II;
                break;
            default:
                second = new Rotor(SWISSK_III, first_position);
                wiring2 = SWISSK_III;
                break;
        }

        init[1] = second_position;

        switch (third_wiring) {
            case "SWISSK I":
                third = new Rotor(SWISSK_I, first_position);
                wiring3 = SWISSK_II;
                break;
            case "SWISSK II":
                third = new Rotor(SWISSK_II, first_position);
                wiring3 = SWISSK_II;
                break;
            default:
                third = new Rotor(SWISSK_III, first_position);
                wiring3 = SWISSK_III;
                break;
        }

        init[2] = third_position;
    }

    void setPlugboard(){

    }

    Enigma restore(){

        return new Enigma(wiring1, init[0], wiring2, init[1], wiring3, init[2], pairs);

    }

    private static char numToLetter(int letter){

        switch(letter){
            case 0: return 'A';
            case 1: return 'B';
            case 2: return 'C';
            case 3: return 'D';
            case 4: return 'E';
            case 5: return 'F';
            case 6: return 'G';
            case 7: return 'H';
            case 8: return 'I';
            case 9: return 'J';
            case 10: return 'K';
            case 11: return 'L';
            case 12: return 'M';
            case 13: return 'N';
            case 14: return 'O';
            case 15: return 'P';
            case 16: return 'Q';
            case 17: return 'R';
            case 18: return 'S';
            case 19: return 'T';
            case 20: return 'U';
            case 21: return 'V';
            case 22: return 'W';
            case 23: return 'X';
            case 24: return 'Y';
            case 25: return 'Z';
        }

        return 'Ñ';
    }

    static int letterToNum(char letter){

        switch(letter){
            case 'A': return 0;
            case 'B': return 1;
            case 'C': return 2;
            case 'D': return 3;
            case 'E': return 4;
            case 'F': return 5;
            case 'G': return 6;
            case 'H': return 7;
            case 'I': return 8;
            case 'J': return 9;
            case 'K': return 10;
            case 'L': return 11;
            case 'M': return 12;
            case 'N': return 13;
            case 'O': return 14;
            case 'P': return 15;
            case 'Q': return 16;
            case 'R': return 17;
            case 'S': return 18;
            case 'T': return 19;
            case 'U': return 20;
            case 'V': return 21;
            case 'W': return 22;
            case 'X': return 23;
            case 'Y': return 24;
            case 'Z': return 25;
            case ' ': return 26;
        }

        return -1;
    }

}
