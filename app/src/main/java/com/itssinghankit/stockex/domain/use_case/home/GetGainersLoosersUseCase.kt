package com.itssinghankit.stockex.domain.use_case.home

import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetGainersLoosersUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
) {
    suspend operator fun invoke()=repositoryInterface.getGainersLoosers()
}