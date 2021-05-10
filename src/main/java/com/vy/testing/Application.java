package com.vy.testing;

import com.vy.testing.entity.Phone;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

public class Application {
    public static void main(String... args){
        scriptEngine();
        context();
        contextFile();
        contextWithMember();
    }

    public static void scriptEngine(){

        String jsCode = "console.log('Hello World')";

        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("graal.js");
        if(scriptEngine !=null){
            try{
                scriptEngine.eval(jsCode);
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }else{
            System.out.println("GraalVM Script Engine is not loaded.");
        }
    }


    public static void context(){
        String JAVASCRIPT_FUNCTION = ""
                + "function helloWorld() {\n"
                + "    print('hello world from Function')\n"
                + "}\n";

        try(Context graalContext = Context.create()){
            graalContext.eval(Source.newBuilder("js", JAVASCRIPT_FUNCTION, "jsFile.js").build());
            Value helloWorldFunction = graalContext.getBindings("js").getMember("helloWorld");
            helloWorldFunction.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void contextFile(){
        try(Context graalContext = Context.create()){
            File jsFile = new File("src/main/resources/jsFile.js");
            graalContext.eval(Source.newBuilder("js", jsFile).build());
            Value helloWorldFunction = graalContext.getBindings("js").getMember("helloWorld");
            helloWorldFunction.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void contextWithMember() {
        Phone phone1 = new Phone("123456","972");
        Object phone = new Phone("123456","972");;

        String printNumberScript = "console.log(phone.number)";
        String callingScript = "phone.call('Someone')";

        Context context = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).build();

        context.getBindings("js").putMember("_this", phone);
        //context.getBindings("js").putMember("_this.extension", phone.);

        context.eval("js", "console.log('Hello from the project')");
        context.eval("js", "console.log(_this)");
        context.eval("js", "console.log(_this.getExtension())");
        context.eval("js", "console.log(JSON.stringify(_this))");
        context.eval("js", "console.log(JSON.stringify(_this.getExtension()))");
        context.eval("js", "console.log(_this.number)");
        context.eval("js", "console.log(_this.getExtension().getExtension())");
        context.eval("js", "console.log([12345,123456].includes(_this.number));");
        context.eval("js", "var x = ['12345','123456'].includes(_this.number);");
        context.eval("js", "var y = ['972','234'].includes(_this.getExtension().getExtension());");


        System.out.println("x: " +
                context.getBindings("js").getMember("x").asBoolean()
        );
        System.out.println("y: " +
                context.getBindings("js").getMember("y").asBoolean()
        );

        Object x = null;
        x = context.getBindings("js").getMember("x").asBoolean();
        System.out.println(x);
        Object y = context.getBindings("js").getMember("y").asBoolean();
        System.out.println(y);

    }
}