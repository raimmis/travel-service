package com.muizarajs.travel.service.routeprice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class DraftPriceController {
    private final DraftPriceService draftPriceService;

    @PostMapping
    public ResponseEntity<DraftPriceResponse> findPrice(@Valid @RequestBody DraftPriceRequest draftPriceRequest) {
        var price = draftPriceService.calculatePrice(draftPriceRequest);
        return ResponseEntity.ok(price);
    }
}
