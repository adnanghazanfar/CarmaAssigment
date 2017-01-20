package com.assignment.project.faces.components;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.model.TreeNode;
import org.springframework.util.CollectionUtils;

@FacesComponent(createTag = true, namespace = "http://carmataglib.com/component", tagName = "collapsible-tree-menu", value = "com.assignment.project.faces.components.CollapsibleTreeMenuComponent")
public class CollapsibleTreeMenuComponent extends UIComponentBase implements ClientBehaviorHolder {

	@Override
	public String getFamily() {
		return "ag.custom.component";
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		TreeNode root = (TreeNode) getAttributes().get("value");
		if (root != null) {

			ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, this,
					"click", getClientId(context), null);

			ResponseWriter writer = context.getResponseWriter();
			Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
			if (behaviors.containsKey("click")) {
				String click = behaviors.get("click").get(0).getScript(behaviorContext);
				writer.writeAttribute("onclick", click, null);
			}

			writer.startElement("div", null);
			writer.writeAttribute("id", getClientId(context), "id");
			writer.writeAttribute("name", getClientId(context), "clientId");
			writer.write("Click me!");
			writer.endElement("div");

			writer.write("<div class='menu'>");
			writer.write("<div class='accordion'>");

			traverse(root, writer);

			writer.write("</div>");
			writer.write("</div>");

		}
	}

	public void traverse(TreeNode root, ResponseWriter writer) throws IOException {
		String name = root.getData().toString();
		String hrefId = makeUniqueHref(name);

		writer.write("<div class='accordion-group'>");

		// HEADING START
		writer.write("<div class='accordion-heading area'>");
		writer.write("<a class='accordion-toggle' data-toggle='collapse' href='#" + hrefId + "'>");
		writer.write(name);
		writer.write("</a>");
		writer.write("</div>");
		// HEADING END

		// BODY START
		writer.write("<div class='accordion-body collapse' id='" + hrefId + "'>");
		writer.write("<div class='accordion-inner'>");

		for (TreeNode node : root.getChildren()) {
			if (!CollectionUtils.isEmpty(node.getChildren())) {
				traverse(node, writer);
				// BODY END
				closeBody(writer);
			} else {
				loadLeaves(writer, root.getChildren());
				return;
			}
		}
		// BODY END ROOT
		closeBody(writer);
	}

	private void closeBody(ResponseWriter writer) throws IOException {
		writer.write("</div>");
		writer.write("</div>");
		writer.write("</div>");
	}

	private void loadLeaves(ResponseWriter writer, List<TreeNode> list) throws IOException {

		writer.write(" <ul class='dropdown-menut'>");
		for (TreeNode node : list) {
			writer.write("<li>");
			writer.write("<a class='accordion-toggle' data-toggle='collapse' href='#'>");
			writer.write(node.getData().toString());
			writer.write("</a>");
			writer.write("</li>");
		}
		writer.write("</ul>");
	}

	private String makeUniqueHref(String name) {
		name = name.toLowerCase().replaceAll(" ", "");
		return name;
	}

}
