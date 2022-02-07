package ControlService.Controllers;

import ControlService.Entities.AccumulatorE;
import ControlService.Repositories.AccumulatorRepository;
import ControlService.vo.AccumulatorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/accumulators")
@Component
public class AccumulatorController {
    @Autowired
    private AccumulatorRepository accumulatorRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<AccumulatorE> getAll() {
        return accumulatorRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    AccumulatorE getById(@PathVariable String id) {
        Optional<AccumulatorE> accumulator = accumulatorRepository.findById(id);
        return accumulator.get();
    }

    @PostMapping(path="/{id}")
    public @ResponseBody
    void updateById(@PathVariable String id, @RequestBody AccumulatorVO accumulatorVO) {
        AccumulatorE accumulator = AccumulatorE.fromVO(accumulatorVO);
        accumulatorRepository.save(accumulator);
    }
}
