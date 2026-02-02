package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鐢ㄩ€旓細鎻愪緵 IAM 鏈嶅姟鐨勫熀纭€鍋ュ悍妫€鏌ョ鐐广€? * 杈圭晫锛氫粎鐢ㄤ簬鍙敤鎬ф帰娴嬶紝涓嶆壙杞戒笟鍔℃暟鎹€? * 绾跨▼妯″瀷锛氭棤鐘舵€?Controller锛岀嚎绋嬪畨鍏ㄣ€? * 璋冪敤鏃舵満锛氳繍缁存垨缃戝叧鎺㈡祴鏃惰皟鐢ㄣ€? * 娓呯悊璐ｄ换锛氭棤銆? * 璇敤椋庨櫓锛氫笉瑕佸湪姝ゆ帴鍙ｈ繑鍥炴晱鎰熶俊鎭€? * 涓庡叾瀹冪粍浠跺叧绯伙細鍝嶅簲浣撲娇鐢?{@link ApiResponse} 鍖呰銆? */
@RestController
public class HealthController {

    /**
     * 鍏ュ弬锛氭棤銆?     * 杩斿洖锛氱粺涓€鎴愬姛鍝嶅簲锛坉ata 涓?"OK"锛夈€?     * 鍓綔鐢細浼氬湪鍝嶅簲涓惡甯?requestId銆?     * 鏄惁鍙┖锛氳繑鍥炲€间笉涓虹┖銆?     * 寮傚父锛氭棤銆?     * 绾跨▼瀹夊叏锛氱嚎绋嬪畨鍏紙鏃犲叡浜姸鎬侊級銆?     * 浣跨敤寤鸿锛氫粎鐢ㄤ簬鍋ュ悍鎺㈡祴锛岄伩鍏嶄綔涓轰笟鍔℃帴鍙ｄ緷璧栥€?     *
     * @return ApiResponse<String>
     */
    @GetMapping("/api/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("OK");
    }
}