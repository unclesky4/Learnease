package org.jyu.web.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jyu.web.dao.manage.PaperLabelRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.manage.LabelJson;
import org.jyu.web.entity.paper.PaperLabel;
import org.jyu.web.service.manage.PaperLabelService;
import org.jyu.web.utils.DateUtil;
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
		label.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
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
		Sort sort = new Sort(Direction.DESC, "createTime");
		if (sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC, "createDate");
		}
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Page<PaperLabel> page = paperLabelDao.findAll(pageable);
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", convertList(page.getContent()));
		return map;
	}

	@Override
	public List<PaperLabel> list() {
		return paperLabelDao.findAll();
	}
	
	List<LabelJson> convertList(List<PaperLabel> paperLabels) {
		List<LabelJson> list = new ArrayList<>();
		if (paperLabels == null) {
			return list;
		}
		for (PaperLabel paperLabel : paperLabels) {
			LabelJson json = new LabelJson();
			json.setId(paperLabel.getId());
			json.setCreateTime(paperLabel.getCreateTime());
			json.setName(paperLabel.getName());
			list.add(json);
		}
		return list;
	}

}
