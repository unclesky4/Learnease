package org.jyu.web.service.paper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dao.paper.PaperLabelRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.entity.paper.PaperLabel;
import org.jyu.web.service.paper.PaperLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaperLabelServiceImpl implements PaperLabelService {
	
	@Autowired
	private PaperLabelRepository paperLabelDao;

	@Transactional
	@Override
	public Result save(String name) {
		if (paperLabelDao.findByName(name) != null) {
			return new Result(false, "标签已存在");
		}
		PaperLabel label = new PaperLabel();
		label.setName(name);
		paperLabelDao.saveAndFlush(label);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String name) {
		PaperLabel label = paperLabelDao.getOne(id);
		label.setName(name);
		paperLabelDao.saveAndFlush(label);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result deleteByid(String id) {
		paperLabelDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public Map<String, Object> pageJson(Integer pageNumber, Integer pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		if (sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC, "createDate");
		}
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Page<PaperLabel> page = paperLabelDao.findAll(pageable);
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		return map;
	}

	@Override
	public List<PaperLabel> list() {
		return paperLabelDao.findAll();
	}

}
