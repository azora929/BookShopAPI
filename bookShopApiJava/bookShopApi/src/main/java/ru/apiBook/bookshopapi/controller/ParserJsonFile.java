package ru.apiBook.bookshopapi.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.apiBook.bookshopapi.entity.Root;

import java.io.FileReader;
import java.io.FileWriter;

public class ParserJsonFile {
    Logger parserLogger = LoggerFactory.getLogger(ParserJsonFile.class);
    public Root parseJson(){
        parserLogger.debug("doParseJson");
        Gson fileGson = new Gson();
        try(FileReader fileReader = new FileReader("bookShopApi/data.json")){return fileGson.fromJson(fileReader, Root.class);}
        catch (Exception e){e.printStackTrace(); parserLogger.warn("Fail to open or read file");}
        parserLogger.debug("endDoParseJson");
        return null;
    }

    public void toJson(Root root){
        parserLogger.debug("doToJson");
        Gson fileGson = new Gson();
        try(FileWriter fileWriter = new FileWriter("bookShopApi/data.json")){fileGson.toJson(root, fileWriter);}
        catch (Exception e){e.printStackTrace(); parserLogger.warn("Fail to open or write file");}
        parserLogger.debug("endDoToJson");
    }
}
