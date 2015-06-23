package controllers;

import com.company.config.ConfigProvider;
import com.company.merge.MergeByDOMService;
import com.company.merge.MergeByObjectsModelService;
import com.company.merge.MergeService;
import play.mvc.*;

public class Application extends Controller {

    private static MergeService mergeService = new MergeByDOMService(new ConfigProvider().get());

    public static Result index() {
        return ok(index.render());
    }

    public static Result download(){
        return ok(mergeService.getYml()).as("application/xml");
    }

}
