package com.liee.report.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
 
/**
 * @author yuerzm
 * @date 2015年10月22日
 */
public class VelocityLayoutToolbox20View extends VelocityLayoutView {
 
    private static ToolboxFactory   toolboxFactory  = null;
 
    @Override
    protected Context createVelocityContext(Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // Create a ChainedContext instance.
        ViewToolContext vtc;
 
        vtc = new ViewToolContext(getVelocityEngine(), request, response,
                getServletContext());
 
        vtc.putAll(model);
 
        if (toolboxFactory == null) {
            ToolManager toolManager = new ToolManager();
            toolManager.setVelocityEngine(getVelocityEngine());
            toolManager.configure(getServletContext().getRealPath(
                    getToolboxConfigLocation()));
            toolboxFactory = toolManager.getToolboxFactory();
        }
 
        if (this.getToolboxConfigLocation() != null) {
 
            if (toolboxFactory.hasTools(Scope.REQUEST)) {
                vtc.addToolbox(toolboxFactory.createToolbox(Scope.REQUEST));
            }
            if (toolboxFactory.hasTools(Scope.APPLICATION)) {
                vtc.addToolbox(toolboxFactory.createToolbox(Scope.APPLICATION));
            }
            if (toolboxFactory.hasTools(Scope.SESSION)) {
                vtc.addToolbox(toolboxFactory.createToolbox(Scope.SESSION));
            }
        }
        return vtc;
    }
}