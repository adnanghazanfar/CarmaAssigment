package com.assignment.project.faces.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "collapsibleTreeBean")
@ViewScoped
public class CollapsibleTreeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TreeNode root;

	@PostConstruct
	public void init() {
		root = new DefaultTreeNode("Root", null);

		TreeNode node0 = new DefaultTreeNode("Node 0", root);
		TreeNode node1 = new DefaultTreeNode("Node 1", root);

		new DefaultTreeNode("Node 0.0", node0);
		new DefaultTreeNode("Node 0.1", node0);
		new DefaultTreeNode("Node 1.0", node1);
		new DefaultTreeNode("Node 1.1", node1);
		new DefaultTreeNode("Node 1.2", node1);
		new DefaultTreeNode("Node 1.3", node1);
	}
	
	public void selectedNode(AjaxBehaviorEvent event){
		System.out.println("Method Called");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Selected Leaf"));
	}
	
	public TreeNode getRoot() {
		return root;
	}
}