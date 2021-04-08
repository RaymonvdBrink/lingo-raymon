package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private  final TrainerService service;
    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @GetMapping("/game/all")
    public List<Game> getAll(){
        return service.getAll();
    }

    @PostMapping("/game/newGame")
    public Progress newGame(){
        return service.newGame();
    }

    @PostMapping("/game/{id}/newRound")
    public Progress newRound(@PathVariable long id){
        return service.newRound(id);
    }

    @PostMapping("/game/{id}/{guess}")
    public Progress guess(@PathVariable long id, @PathVariable String guess){
        return service.guessWord(guess, id);
    }
}
