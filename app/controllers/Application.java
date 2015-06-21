package controllers;

import com.company.config.Config;
import com.company.config.ConfigProvider;
import com.company.merge.MergeService;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    private static MergeService mergeService = new MergeService(new ConfigProvider().get());

    public static Result index() {
        return ok(index.render());
    }

    public static Result download(){
        return ok(mergeService.getYml()).as("application/xml");
    }

}
