/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.calculator.server;

/**
 * Class transforms given string equation into numbers and compute to result
 * @author  Rafal Jablonowski
 */
public class Calculate {
        /*
         *  Special numbers tabel
         */
        private static final Object[][] special_numerals = {

           {"vierzig", 40}, // 40
           {"fünfzig", 50},// 50
           {"achtzig", 80}, // 80
           {"neunzig", 90}, //90
           {"millionen", 1000000}
        };
        /*
         *  numbers tabel
         */
        private static final Object[][] numerals = {
           {"null", 0},
           {"ein", 1},
           {"eins", 1},
           {"zwei", 2},
           {"drei", 3},
           {"vier", 4},
           {"fünf", 5},
           {"sechs", 6},
           {"sieben", 7},
           {"acht", 8},
           {"neun", 9},
           {"zehn", 10},
           {"elf", 11},
           {"zwölf", 12},
           {"dreizehn", 13},
           {"vierzehn", 14},
           {"fünfzehn", 15},
           {"sechzehn", 16},
           {"siebzehn", 17},
           {"achtzehn", 18},
           {"neunzehn", 19},
           {"zwanzig", 20},
           {"ßig", 27}, // 30
           {"_vier", 40},
           {"_fünf", 50},
           {"sechzig", 60},
           {"siebzig", 70},
           {"_acht", 80},
           {"_neun", 90},
           {"hert", 100}, // einhundert - "und" case
           {"tausend", 1000},
           {"eintausend", 1000},
           {"million", 1000000},
           {"minus", 0}, // -
        };
        /*
         *  Recived equation from the server
         */
        String equation;

        /*
         * Main constructor
         * @param Given equation
         */
        Calculate(String equation){
            this.equation = equation;
        }

        /*
         * Method changes given text into numbers and calculate the result
         */
        public float text2num() throws Exception {

           float result = 0;

           // + plus - minus * mal / divide
           equation = equation.replaceAll("\\+", "plus");
           equation = equation.replaceAll("-", "minus");
           equation = equation.replaceAll("\\*", "mal");
           equation = equation.replaceAll("\\/", "divide");

           String[] component = equation.toLowerCase().split("\\s+");
           float num_component[] = new float[component.length];


           int j = -1;
           for (String word : component) {
               ++j;
               num_component[j] = 0;

               // word is a sign
               if(word.equals("plus") || word.equals("minus") ||
                   word.equals("mal")  || word.equals("divide") )
                   continue;
               else if(check(word)){ // word is a number
                   num_component[j] = Float.parseFloat(word);
               }
               else {  // convert word into number

                   num_component[j] = wordToFloat(word);


               }
           }
           result = num_component[0];
           for(int i = 0 ; i < component.length - 1; i++){

               if(component[i].equals("plus"))
               {
                   result += num_component[i + 1];
               }
               else if(component[i].equals("minus"))
                       result -= num_component[i+1];
               else if(component[i].equals("mal"))
                       result *= num_component[i+1];
               else if(component[i].equals("divide"))
               {
                   if(num_component[i+1] != 0)
                       result = result / num_component[i+1];   // if different than 0
                   else if(num_component[i+1] == 0){
                    System.err.println("Dividing through zero");
                       result = 0;
                   }
               }
           }
           return result;
        }
        
        /*
         * Method checks if given word is a number
         * @param One word of the equation
         */
       static boolean check(String word){
            for(int i = 0 ; i < 10 ; i++)
                if(word.substring(0, 1).equals(String.valueOf(i))){
                    return true;
                }
            return false;
        }
        /*
         * Method changes word into number
         * @param One word of the equation
         */
    static float wordToFloat(String equation) throws Exception {

           equation = equation.replaceAll("und", "");  // removing und

           float value = 0;

           for(int i = 0; i < special_numerals.length; i++) // recognizing words
                equation = equation.toLowerCase().replaceAll(special_numerals[i][0].toString(), "_" + special_numerals[i][0].toString().replaceAll("zig", "") );
          // equation = equation

           for(int i = 0; i < numerals.length; i++) // recognizing words
                equation = equation.toLowerCase().replaceAll(numerals[i][0].toString(), numerals[i][0].toString()+"\n");

           String[] words = equation.split("\\s+");
           float g = 0;
           float n = 0;

            for (String word : words) {
                boolean exception= true;
                int i;
                for(i = 0; i < numerals.length; i++)
                    if (numerals[i][0].toString().equals(word)) {
                        exception = false;
                        break;
                    }
                if(word.equals("s"))
                    continue;
                if(exception)
                    throw new Exception("Unknown token : " + word);

                float tempval = Integer.parseInt(numerals[i][1].toString());

                if(tempval < 100)
                    g+= tempval;
                else if (tempval == 100)
                {
                    if(g==0)
                    {
                        g = 1;
                        g*=100;
                    } else
                        g*=100;
                }
                else
                {
                    if(g==0)
                        g = 1;
                    n+= g * tempval;
                    g = 0;
                }
            }
            value = n + g;
            if(words[0].equals("minus"))
                value=-value;

            return value;
    }
}
