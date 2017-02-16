package TrackController.Classes;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PLC
    {
        boolean isGood;
        String input, output;
        String[] inOut, afterParenths;
        public PLC(File file)
        {
            isGood = true;
            try(BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                for(String line; (line = br.readLine()) != null; )
                {
                    System.out.println("Whole string: " + line + "\n");
                    if(!line.contains("="))
                    {
                        isGood = false;
                    }
                    else {
                        inOut = line.split("=");
                        if (inOut.length != 2)
                        {
                            isGood = false;
                        }
                        else
                        {
                            output = inOut[0].trim();
                            input = inOut[1].trim();

                            System.out.println("output: " + output + "\n" + "input: " + input);

                            try {

                                ScriptEngineManager sem = new ScriptEngineManager();
                                ScriptEngine se = sem.getEngineByName("JavaScript");
                                String myExpression = "('abc' == 'xyz' && 'thy' == 'thy') || ('ujy' == 'ujy')";
                                System.out.println(se.eval(myExpression));

                            } catch (ScriptException e) {

                                System.out.println("Invalid Expression");
                                e.printStackTrace();

                            }
                           /* if (line.contains("("))
                            {
                                afterParenths = input.split("\\(|\\)");
                                afterParenths = removeEmptyElements(afterParenths);
                                for (int i = 0; i < afterParenths.length; i++)
                                {
                                    System.out.println(i + ") " + afterParenths[i].trim());
                                }
                            }*/
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("There is an exception: " + e.toString());
            }
        }

        private boolean OnlyVariable(String test)
        {
            return(true);
        }

        private String[] removeEmptyElements(String[] test)
        {
            ArrayList<String> result = new ArrayList<>();

            for(String item : test)
                if(!item.equals(""))
                    result.add(item);

            result.toArray(test);

            return result.toArray(test);
        }

        public boolean isValid()
        {
            return(isGood);
        }
    }