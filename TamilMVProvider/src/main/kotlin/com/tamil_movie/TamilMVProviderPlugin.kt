package com.tamil_movie

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.BasePlugin
import android.content.Context

@CloudstreamPlugin
class TamilMVProviderPlugin: BasePlugin() {
    override fun load() {
        registerMainAPI(TamilMVProvider())
    }
}
