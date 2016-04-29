import java.util.*;
import java.nio.charset.Charset;
import java.io.*;
/**
 *
 * @author Arooj
 */
class Variable
{
    String name;
    int value;
    
    public String getname()
    {
        return name;
    }
    
    public void setname(String n)
    {
        name = n;
    }
    
    public int getvalue()
    {
        return value;
    }
    
    public void setvalue(int v)
    {
        value = v;
    } 
}

public class Interpreter 
{
    static Vector<Variable> myvector = new Vector<Variable>();

    public static void main(String[] args) throws IOException, FileNotFoundException
    {
        String path = "file.txt";
        String line;
        
        try (
            InputStream is = new FileInputStream(path);
            InputStreamReader ir = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(ir);
            )
        {
            while ((line = reader.readLine()) != null)
            {
                String[] word = line.split("(=) | ( ) | (;)");
                if (word[0].equals("Let"))
                {
                    if (!word[1].equals("") && !word[2].equals(""))
                    {
                        Variable var = new Variable();
                        var.setname(word[1]);
                        var.setvalue(Integer.parseInt(word[2]));
                        myvector.add(var);
                    }
                    else if (word[0].equals("print"))
                    {
                        Variable var = new Variable();
                        System.out.println("The value of " + var.name + "is: " + var.value);
                    }
                    else
                    {
                        if(line.contains("="))
                        {
                            String[] w = line.split("=");
                            Variable var = new Variable();
                            var.setname(w[0]);
                            int e = eval(w[1]);
                            var.setvalue(e);
                        }
                    }
                }
            }
        }
    }
    
    public static int eval(String exp)
    {
        int val = 0;
        String[] token = exp.split("(?<=\\+)|(?=\\+)|(;)");
        for(int i = 0; i < token.length; i++)
        {
            if(isNumeric(token[i]))
            {
               val = Integer.parseInt(token[i]);
            }
            else if(token[i].equals("+") || token[i].equals("-") || token[i].equals("*") || token[i].equals("/"))
            {
                if (token[i].equals("+"))
                {
                    val = Integer.parseInt(token[i - 1]) + Integer.parseInt(token[i + 1]);
                }
                if (token[i].equals("-"))
                {
                    val = Integer.parseInt(token[i - 1]) - Integer.parseInt(token[i + 1]);
                }
                if (token[i].equals("*"))
                {
                    val = Integer.parseInt(token[i - 1]) * Integer.parseInt(token[i + 1]);
                }
                if (token[i].equals("/"))
                {
                    val = Integer.parseInt(token[i - 1]) / Integer.parseInt(token[i + 1]);
                }
            }
        }
        return val;
    }
    
    public static boolean isNumeric(String str) 
    {
        try
        {
            int a = Integer.parseInt(str);            
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}