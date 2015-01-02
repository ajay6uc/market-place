package com.marketplace.service.node;

import java.util.List;

import com.marketplace.dataaccess.node.Node;
import com.marketplace.shared.common.framework.service.DBService;

public interface NodeService extends DBService<Node> {
	
	
	public List<Node> getChildren(Long parentId);

}
