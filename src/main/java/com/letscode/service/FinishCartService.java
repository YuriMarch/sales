package com.letscode.service;

import com.letscode.client.ShopperClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinishCartService {

    private final SaleRepositoryService saleRepositoryService;
    private final ShopperClientService shopperClientService;
}
