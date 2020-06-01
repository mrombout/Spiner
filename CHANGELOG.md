# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Added support for "->" style links (#61).

### Changed

- Update to run on Java 11.
- Improve Twine parser to be more lenient by ignoring additional unsupported attributes.
- Improve ExtendedTwine parser to be more lenient by ignoring order on xtw-metadata.
- Replaced pegdown parser with flexmark-java (#20).

### Security

- Disabled XML external entity processing (java:S2755).

### Fixes

- Fixed embedding images in an EPUB file.

## [0.1.2] - 2019-03-10

### Security

- Disabled XML external entity processing.

## [0.1.1] - 2018-01-21

### Changed

- Updated Twine parser to support Twine 2.2.1.

### Fixed

- Fixed EPUB transformer to export in a valid EPUB format.

## [0.1.0] - 2017-10-01

- Initial release

[Unreleased]: https://github.com/mrombout/Spiner/compare/v0.1.2...HEAD
[0.1.2]: https://github.com/mrombout/Spiner/compare/v0.1.1...v0.1.2
[0.1.1]: https://github.com/mrombout/Spiner/compare/v0.1.0...v0.1.1
[0.1.0]: https://github.com/mrombout/Spiner/releases/tag/v0.1.0