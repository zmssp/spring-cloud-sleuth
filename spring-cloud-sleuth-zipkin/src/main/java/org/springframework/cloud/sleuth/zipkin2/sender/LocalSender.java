/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.sleuth.zipkin2.sender;

import java.util.List;

import zipkin2.Call;
import zipkin2.Span;
import zipkin2.codec.BytesEncoder;
import zipkin2.codec.Encoding;
import zipkin2.reporter.BytesMessageEncoder;
import zipkin2.reporter.Sender;

public class LocalSender extends Sender {

	private final Encoding encoding;

	private final BytesMessageEncoder messageEncoder;

	LocalSender(BytesEncoder<Span> encoder) {
		this.encoding = encoder.encoding();
		this.messageEncoder = BytesMessageEncoder.forEncoding(this.encoding);
	}

	@Override
	public Encoding encoding() {
		return this.encoding;
	}

	@Override
	public int messageMaxBytes() {
		return 5 * 1024 * 1024;
	}

	@Override
	public int messageSizeInBytes(List<byte[]> spans) {
		return encoding().listSizeInBytes(spans);
	}

	@Override
	public Call<Void> sendSpans(List<byte[]> encodedSpans) {
		byte[] message = this.messageEncoder.encode(encodedSpans);
		return null;
	}

}
