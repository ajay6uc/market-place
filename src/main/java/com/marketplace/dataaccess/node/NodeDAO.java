package com.marketplace.dataaccess.node;

import java.util.List;

import com.marketplace.shared.common.framework.dao.DBDAO;

public interface NodeDAO extends DBDAO<Node>{
	
	public List<Node> getChildren(Long parentId);

}
