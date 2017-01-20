package com.assignment.project.faces.components;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.model.TreeNode;
import org.springframework.util.CollectionUtils;

@FacesComponent(createTag = true, namespace = "http://carmataglib.com/component", tagName = "collapsible-tree-menu", value = "com.assignment.project.faces.components.CollapsibleTreeMenuComponent")
public class CollapsibleTreeMenuComponent extends UIComponentBase implements ClientBehaviorHolder {

	private static final String CUSTOM_COMPONENT_TAG_LIB = "ag.custom.component";
	private static final String EVENT_AJAX_CLICK = "click";

	@Override
	public String getFamily() {
		return CUSTOM_COMPONENT_TAG_LIB;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		TreeNode root = (TreeNode) getAttributes().get("value");
		if (root != null) {
			ResponseWriter writer = context.getResponseWriter();
			writer.write("<div class='menu'>");
			writer.write("<div class='accordion'>");
			traverse(context, root, writer);
			writer.write("</div>");
			writer.write("</div>");

		}
	}

	private String getAttachedEvent(FacesContext context, String eventName) throws IOException {
		ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, this,
				eventName, getClientId(context), null);
		Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		String click = null;
		if (behaviors.containsKey(eventName)) {
			click = behaviors.get(eventName).get(0).getScript(behaviorContext);
		}
		return click;
	}

	public void traverse(FacesContext context, TreeNode root, ResponseWriter writer) throws IOException {
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
				traverse(context, node, writer);
				// BODY END
				closeBody(writer);
			} else {
				loadLeaves(context, writer, root.getChildren());
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

	private void loadLeaves(FacesContext context, ResponseWriter writer, List<TreeNode> list) throws IOException {

		writer.write(" <ul class='dropdown-menut'>");
		String click = getAttachedEvent(context, EVENT_AJAX_CLICK);
		String componentId = getClientId(context);

		int i = 0;
		for (TreeNode node : list) {
			writer.write("<li>");

			String leafId = componentId + "-leaf-" + i;

			writer.startElement("a", null);
			writer.writeAttribute("class", "accordion-toggle", null);
			writer.writeAttribute("data-toggle", "collapse", null);
			writer.writeAttribute("href", "#", null);
			writer.writeAttribute("id", componentId + "-leaf-" + i, "id");
			writer.writeAttribute("name", componentId, "clientId");
			writer.writeAttribute("onclick", click.replaceAll(componentId, leafId), node.getData().toString());
			writer.write(node.getData().toString());
			writer.endElement("a");

			writer.write("</li>");
			i++;
		}
		writer.write("</ul>");
	}

	private String makeUniqueHref(String name) {
		name = name.toLowerCase().replaceAll(" ", "");
		return name;
	}

	@Override
	public void decode(FacesContext context) {
		Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.isEmpty()) {
			return;
		}
		ExternalContext external = context.getExternalContext();
		Map<String, String> params = external.getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");

		if (behaviorEvent != null) {
			List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

			if (behaviors.size() > 0) {
				String behaviorSource = params.get("javax.faces.source");
				String clientId = getClientId(context);
				if (behaviorSource != null && behaviorSource.startsWith(clientId)) {
					for (ClientBehavior behavior : behaviorsForEvent) {
						behavior.decode(context, this);
					}
				}
			}
		}
	}

	@Override
	public Collection<String> getEventNames() {
		return Arrays.asList(EVENT_AJAX_CLICK);
	}

	@Override
	public String getDefaultEventName() {
		return EVENT_AJAX_CLICK;
	}

}
