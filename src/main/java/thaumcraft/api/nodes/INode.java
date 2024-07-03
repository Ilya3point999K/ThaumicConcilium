package thaumcraft.api.nodes;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public interface INode extends IAspectContainer {

	/**
	 * Unique identifier to distinguish nodes. Normal node id's are based on world id and coordinates
	 * @return
	 */
    String getId();
	
	AspectList getAspectsBase();
	
	/**
	 * Return the type of node
	 * @return
	 */
    NodeType getNodeType();

	/**
	 * Set the type of node
	 * @return
	 */
    void setNodeType(NodeType nodeType);

	/**
	 * Set the node modifier
	 * @return
	 */
    void setNodeModifier(NodeModifier nodeModifier);
	
	/**
	 * Return the node modifier
	 * @return
	 */
    NodeModifier getNodeModifier();
		
	/**
	 * Return the maximum capacity of each aspect the node can hold
	 * @return
	 */
    int getNodeVisBase(Aspect aspect);

	/**
	 * Set the maximum capacity of each aspect the node can hold
	 * @return
	 */
    void setNodeVisBase(Aspect aspect, short nodeVisBase);
	
}
