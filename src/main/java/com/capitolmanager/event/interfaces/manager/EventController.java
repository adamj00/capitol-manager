
package com.capitolmanager.event.interfaces.manager;

import static com.capitolmanager.utils.DateUtils.formatLocalDateToDDMM;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.event.application.EventApplicationService;
import com.capitolmanager.event.application.EventFormFactory;
import com.capitolmanager.show.application.ShowApplicationService;


@Controller
@RequestMapping("/events")
public class EventController {

	private static final String VIEW_NAME = "events";
	private static final String M_EVENT_FORM = "eventForm";
	public static final String REDIRECT = "redirect:";

	private final EventApplicationService eventService;
	private final ShowApplicationService showApplicationService;
	private final EventFormFactory eventFormFactory;

	@Autowired
	EventController(EventApplicationService eventService, ShowApplicationService showApplicationService, EventFormFactory eventFormFactory) {

		Assert.notNull(eventService, "eventService must not be null");
		Assert.notNull(showApplicationService, "showApplicationService must not be null");
		Assert.notNull(eventFormFactory, "eventFormFactory must not be null");

		this.eventService = eventService;
		this.showApplicationService = showApplicationService;
		this.eventFormFactory = eventFormFactory;
	}

	@GetMapping
	public String getEvents(Model model,
		@RequestParam(value = "weekStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart,
		@RequestParam( required = false, value = "eventGroup") Long eventGroupId) {

		if (eventGroupId == null) {

			return REDIRECT + "/eventGroups";
		}

		if (weekStart == null) {

			weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		}

		if (weekStart.getDayOfWeek().getValue() != 1) {

			weekStart = weekStart.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		}

		LocalDate weekEnd = weekStart.plusDays(6);

		model.addAttribute("prevWeekStart", weekStart.minusWeeks(1));
		model.addAttribute("nextWeekStart", weekStart.plusWeeks(1));
		model.addAttribute("weekRange", getCurrentWeekRange(weekStart, weekEnd));

		model.addAttribute("weekDays", eventService.getEventsByWeek(weekStart, eventGroupId));

		model.addAttribute("shows", showApplicationService.getAllShowsEventDto());

		model.addAttribute("weekStart", weekStart);
		model.addAttribute("eventGroup", eventGroupId);

		EventForm form = EventFormFactory.getEmptyForm();
		form.setEventGroupId(eventGroupId);
		model.addAttribute(M_EVENT_FORM, form);

		return VIEW_NAME;
	}

	@PostMapping
	public String saveEvent(@ModelAttribute(M_EVENT_FORM) EventForm eventForm, Model model) {

		if (eventForm.getId() == null) {

			eventService.saveEvent(eventForm);
		}

		else {

			eventService.updateEvent(eventForm);
		}

		return REDIRECT + "events?weekStart=" + eventForm.getEventStartTime().format(DateTimeFormatter.ISO_DATE)
			+ "&eventGroup=" + eventForm.getEventGroupId();

	}



	@GetMapping("/delete")
	String deleteEvent(@RequestParam Long id,
		@RequestParam String weekStart,
		@RequestParam Long eventGroup) {

		eventService.deleteEvent(id);

		return REDIRECT + "/events?weekStart=" + weekStart + "&eventGroup=" + eventGroup;
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventForm> getEvent(@PathVariable Long id) {

		EventForm eventForm = eventFormFactory.getForm(id);

		return ResponseEntity.ok(eventForm);
	}

	private String getCurrentWeekRange(LocalDate weekStart, LocalDate weekEnd) {

		return "Tydzień " + formatLocalDateToDDMM(weekStart) + " - " + formatLocalDateToDDMM(weekEnd);
	}
}