package org.texttechnologylab.project.helper;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for XML file usage
 * @author Giuseppe Abrami
 * this class is the XMLHelper from Giuseppe Abrami from the Musterloesung_Uebung2
 */
public class XMLReader {

    /**
     * List all nodes based on tag-name as part of a given node
     * @param pNode
     * @param sNodeName
     * @return
     */
    public static List<Node> getNodesFromXML(Node pNode, String sNodeName){

        List<Node> rSet = new ArrayList<>(0);

        if(pNode.getNodeName().equals(sNodeName)){
            rSet.add(pNode);
        }
        else {

            if (pNode.hasChildNodes()){
                for (int i = 0; i < pNode.getChildNodes().getLength(); i++){
                    rSet.addAll(getNodesFromXML(pNode.getChildNodes().item(i), sNodeName));
                }
            }
            else {
                if (pNode.getNodeName().equals(sNodeName)){
                    rSet.add(pNode);
                }
            }
        }
        return rSet;
    }

    /**
     * Get one node on a tag-name as part of a given node
     * @param pNode
     * @param sNodeName
     * @return
     */
    public static Node getSingleNodesFromXML(Node pNode, String sNodeName){

        List<Node> nList = getNodesFromXML(pNode, sNodeName);

        if(nList.size()>0){
            return nList.stream().findFirst().get();
        }
        return null;
    }
}
