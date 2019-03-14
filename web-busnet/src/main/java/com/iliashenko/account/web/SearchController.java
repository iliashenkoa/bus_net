package com.iliashenko.account.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iliashenko.account.model.BusStop;
import com.iliashenko.account.service.BusStopService;
import com.iliashenko.account.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private BusStopService stopService;

	@GetMapping(value = "/search_fast")
	public String searchForm(Model model) {
		model.addAttribute("ids", stopService.findAllBusStopsIds());
		return "search_fast";
	}

	@PostMapping(value = "/search_fast")
	public ModelAndView searchFasterstWay(@RequestParam("stop_start") Integer start,
			@RequestParam("stop_end") Integer end, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("stops", searchService.findFastestWay(start, end));
		return new ModelAndView("redirect:way");
	}

	@GetMapping(value = "/way")
	public String showWay(@ModelAttribute("stops") List<BusStop> stops) {
		return "way";
	}

}
