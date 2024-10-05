package com.itssinghankit.stockex.domain.model.home

data class GetGainersLoosersModel (
    val gainers :List<GainersLoosersModel>,
    val loosersModel: List<GainersLoosersModel>
)